package com.huike.contract.service.impl;

import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.service.ISysUserService;
import com.huike.clues.service.ITbRulePoolService;
import com.huike.clues.utils.HuiKeCrmDateUtils;
import com.huike.clues.utils.JobUtils;
import com.huike.common.constant.Constants;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.utils.DateUtils;
import com.huike.contract.domain.vo.TransferVo;
import com.huike.contract.service.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransferServiceImpl implements ITransferService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TbAssignRecordMapper assignRecordMapper;

    @Autowired
    private ITbRulePoolService rulePoolService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TbClueMapper clueMapper;


    @Autowired
    private TbBusinessMapper businessMapper;

//    @Autowired
//    private SysUserMapper sysUserMapper;

    @Override
    public List<TransferVo> selectTransferList(SysUser user) {
        List<TransferVo> transfervoList =new ArrayList<>();
        List<SysUser> list = userService.selectUserList(user);
        for (SysUser sysUser : list) {
            TransferVo transfervo= new TransferVo();
            int clueNum= assignRecordMapper.countAssignCluesByUser(sysUser.getUserId());
            int businessNum= assignRecordMapper.countAssignBusinessByUser(sysUser.getUserId());
            if(clueNum+businessNum>0){
                transfervo.setUserName(sysUser.getUserName());
                transfervo.setClueNum(clueNum);
                transfervo.setBusinessNum(businessNum);
                transfervo.setUserId(sysUser.getUserId());
                transfervo.setPhonenumber(sysUser.getPhonenumber());
                transfervo.setCreateTime(sysUser.getCreateTime());
                transfervoList.add(transfervo);
            }
        }
        return transfervoList;
    }

    @Override
    public Map<String,Object> assignment(String type, Long userId, Long transferUserId) {
        Map<String ,Object> result =new HashMap<>();
    if (TbAssignRecord.RecordType.CLUES.getValue().equals(type)) {
      int clueNum = assignRecordMapper.countAssignCluesByUser(userId);
      if (clueNum >= 0) {
        TbRulePool rulePool = rulePoolService.selectTbRulePoolByType(Constants.rule_type_clue);
        int transferUserClueNum = assignRecordMapper.countAssignCluesByUser(transferUserId);
        // 被转派人保有量达到最大值
        if (transferUserClueNum >= rulePool.getMaxNunmber()) {
            result.put("flag",false);
            result.put("msg","线索转换失败！已经达到被转派人最大保有量");
        } else {
          TbAssignRecord assignRecord = new TbAssignRecord();
          assignRecord.setUserId(userId);
          assignRecord.setLatest("1");
          assignRecord.setType(TbAssignRecord.RecordType.CLUES.getValue());
          List<TbAssignRecord> list = assignRecordMapper.selectAssignRecordList(assignRecord);
          for (int i = 0; i < list.size(); i++) {
            TbAssignRecord tbAssignRecord = list.get(i);
            SysUser sysUser = sysUserMapper.selectUserById(transferUserId);
            tbAssignRecord.setUserId(transferUserId);
            tbAssignRecord.setUserName(sysUser.getUserName());
            tbAssignRecord.setDeptId(sysUser.getDeptId());
            tbAssignRecord.setCreateTime(DateUtils.getNowDate());
            assignRecordMapper.updateAssignRecord(tbAssignRecord);
            clueMapper.setTransfer(tbAssignRecord.getAssignId(), TbClue.StatusType.UNFOLLOWED.getValue());
            Date endDate = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
            clueMapper.updateClueEndTimeById(tbAssignRecord.getAssignId(),endDate);
            if (transferUserClueNum + i >= rulePool.getMaxNunmber()) {
                result.put("flag",false);
                result.put("msg","线索转换失败！已经分配" + i + " 线索");
                break;
            }
          }
        }
      }
    }
        if (TbAssignRecord.RecordType.BUSNIESS.getValue().equals(type)) {
        int busniessNum = assignRecordMapper.countAssignBusinessByUser(userId);
            if (busniessNum >= 0) {
                TbRulePool rulePool = rulePoolService.selectTbRulePoolByType(Constants.rule_type_business);
                int transferUserBusinessNum = assignRecordMapper.countAssignBusinessByUser(transferUserId);
                //被转派人保有量达到最大值
                if (transferUserBusinessNum >= rulePool.getMaxNunmber()) {
                    result.put("flag",false);
                    result.put("msg","线索转换失败！已经达到被转派人最大保有量");
                } else {
                    TbAssignRecord assignRecord = new TbAssignRecord();
                    assignRecord.setUserId(userId);
                    assignRecord.setLatest("1");
                    assignRecord.setType(TbAssignRecord.RecordType.BUSNIESS.getValue());
                    List<TbAssignRecord> list = assignRecordMapper.selectAssignRecordList(assignRecord);
                    for (int i = 0; i < list.size(); i++) {
                        TbAssignRecord tbAssignRecord = list.get(i);
                        SysUser sysUser = sysUserMapper.selectUserById(transferUserId);
                        tbAssignRecord.setUserId(transferUserId);
                        tbAssignRecord.setUserName(sysUser.getUserName());
                        tbAssignRecord.setDeptId(sysUser.getDeptId());
                        tbAssignRecord.setCreateTime(DateUtils.getNowDate());
                        assignRecordMapper.updateAssignRecord(tbAssignRecord);
                        Date endDate = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
                        businessMapper.updateBusinessEndTimeById(tbAssignRecord.getAssignId(),endDate);
                        if (transferUserBusinessNum + i >= rulePool.getMaxNunmber()) {
                            result.put("flag",false);
                            result.put("msg","商机转换失败！已经转派" + i + " 商机");
                            break;
                        }
                    }
                }
            }
        }
        return result;
        }

}
