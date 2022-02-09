package com.huike.contract.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.mapper.mybatis_plus.TbAssignRecordPlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbCluePlusMapper;
import com.huike.clues.service.ISysUserService;
import com.huike.clues.service.ITbRulePoolService;
import com.huike.clues.utils.HuiKeCrmDateUtils;
import com.huike.common.constant.Constants;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.utils.DateUtils;
import com.huike.contract.domain.vo.TransferVo;
import com.huike.contract.service.ITransferService;

@Service
public class TransferServiceImpl implements ITransferService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TbAssignRecordMapper assignRecordMapper;
    
    @Autowired
    private TbAssignRecordPlusMapper assignRecordPlusMapper;

    @Autowired
    private ITbRulePoolService rulePoolService;
    
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TbClueMapper clueMapper;
    
    @Autowired
    private TbCluePlusMapper cluePlusMapper;


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
          /*
           * select id, assign_id, user_id, user_name, dept_id, create_time, create_by,latest, type from tb_assign_record
           *  <if test="assignId != null "> and assign_id = #{assignId}</if>
            <if test="userId != null "> and user_id = #{userId}</if>
            <if test="userName != null  and userName != ''"> and user_name like concat('%', #{userName}, '%')</if>
            <if test="deptId != null "> and dept_id = #{deptId}</if>
            <if test="latest != null  and latest != ''"> and latest = #{latest}</if>
            <if test="type != null  and type != ''"> and type = #{type}</if>
            <if test="params.beginCreateTime != null and params.beginCreateTime != ''"><!-- 开始创建时间 -->
                and date_format(clue.create_time,'%y%m%d') &gt;= date_format(#{params.beginCreateTime},'%y%m%d')
            </if>
            <if test="params.endCreateTime != null and params.endCreateTime != ''"><!--  -->
                and date_format(clue.create_time,'%y%m%d') &lt;= date_format(#{params.endCreateTime},'%y%m%d')
            </if>
           */
          List<TbAssignRecord> list = assignRecordPlusMapper.selectList(new LambdaQueryWrapper<TbAssignRecord>().
        		  eq(ObjectUtils.isNotEmpty(assignRecord.getAssignId()),TbAssignRecord::getAssignId, assignRecord.getAssignId()).
        		  eq(ObjectUtils.isNotEmpty(assignRecord.getUserId()),TbAssignRecord::getAssignId, assignRecord.getAssignId()).
        		  eq(StringUtils.isNotEmpty(assignRecord.getUserName()),TbAssignRecord::getUserName, assignRecord.getUserName()).
        		  eq(ObjectUtils.isNotEmpty(assignRecord.getDeptId()),TbAssignRecord::getDeptId, assignRecord.getDeptId()).
        		  eq(ObjectUtils.isNotEmpty(assignRecord.getLatest()),TbAssignRecord::getLatest, assignRecord.getLatest()).
        		  eq(ObjectUtils.isNotEmpty(assignRecord.getType()),TbAssignRecord::getType, assignRecord.getType()).
        		  between(ObjectUtils.isNotEmpty(assignRecord.getParams().get("beginCreateTime"))
        				  &&
        				  ObjectUtils.isNotEmpty(assignRecord.getParams().get("endCreateTime")),
        				  TbAssignRecord::getCreateTime, assignRecord.getParams().get("beginCreateTime"),assignRecord.getParams().get("endCreateTime"))
        		  );
//          List<TbAssignRecord> list = assignRecordMapper.selectAssignRecordList(assignRecord);
          for (int i = 0; i < list.size(); i++) {
            TbAssignRecord tbAssignRecord = list.get(i);
            SysUser sysUser = sysUserMapper.selectUserById(transferUserId);
            tbAssignRecord.setUserId(transferUserId);
            tbAssignRecord.setUserName(sysUser.getUserName());
            tbAssignRecord.setDeptId(sysUser.getDeptId());
            tbAssignRecord.setCreateTime(DateUtils.getNowDate());
            assignRecordPlusMapper.update(tbAssignRecord, new LambdaUpdateWrapper<TbAssignRecord>().
    				set(TbAssignRecord::getLatest, 0).
    				eq(TbAssignRecord::getType, TbAssignRecord.RecordType.CLUES.getValue()).
    				eq(TbAssignRecord::getAssignId, tbAssignRecord.getId()));
//            assignRecordMapper.updateAssignRecord(tbAssignRecord);
            // update tb_clue set next_time=null, status=#{status}, transfer='1' where id = #{id}
            cluePlusMapper.update(null, new LambdaUpdateWrapper<TbClue>().
            		set(TbClue::getNextTime, null).
            		set(TbClue::getTransfer, 1).
            		set(TbClue::getStatus, TbClue.StatusType.UNFOLLOWED.getValue()));
//            clueMapper.setTransfer(tbAssignRecord.getAssignId(), TbClue.StatusType.UNFOLLOWED.getValue());
            Date endDate = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
            //clueMapper.updateClueEndTimeById(tbAssignRecord.getAssignId(),endDate);
            //update tb_clue set end_time=#{endTime},next_time = null where id = #{id}
            cluePlusMapper.update(null, new LambdaUpdateWrapper<TbClue>().
            		set(TbClue::getEndTime, endDate).
            		set(TbClue::getNextTime, null).
            		set(TbClue::getId, tbAssignRecord.getAssignId())
            		);
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
                    List<TbAssignRecord> list = assignRecordPlusMapper.selectList(new LambdaQueryWrapper<TbAssignRecord>().
                  		  eq(ObjectUtils.isNotEmpty(assignRecord.getAssignId()),TbAssignRecord::getAssignId, assignRecord.getAssignId()).
                  		  eq(ObjectUtils.isNotEmpty(assignRecord.getUserId()),TbAssignRecord::getAssignId, assignRecord.getAssignId()).
                  		  eq(StringUtils.isNotEmpty(assignRecord.getUserName()),TbAssignRecord::getUserName, assignRecord.getUserName()).
                  		  eq(ObjectUtils.isNotEmpty(assignRecord.getDeptId()),TbAssignRecord::getDeptId, assignRecord.getDeptId()).
                  		  eq(ObjectUtils.isNotEmpty(assignRecord.getLatest()),TbAssignRecord::getLatest, assignRecord.getLatest()).
                  		  eq(ObjectUtils.isNotEmpty(assignRecord.getType()),TbAssignRecord::getType, assignRecord.getType()).
                  		  between(ObjectUtils.isNotEmpty(assignRecord.getParams().get("beginCreateTime"))
                  				  &&
                  				  ObjectUtils.isNotEmpty(assignRecord.getParams().get("endCreateTime")),
                  				  TbAssignRecord::getCreateTime, assignRecord.getParams().get("beginCreateTime"),assignRecord.getParams().get("endCreateTime"))
                  		  );
//                    List<TbAssignRecord> list = assignRecordMapper.selectAssignRecordList(assignRecord);
                    for (int i = 0; i < list.size(); i++) {
                        TbAssignRecord tbAssignRecord = list.get(i);
                        SysUser sysUser = sysUserMapper.selectUserById(transferUserId);
                        tbAssignRecord.setUserId(transferUserId);
                        tbAssignRecord.setUserName(sysUser.getUserName());
                        tbAssignRecord.setDeptId(sysUser.getDeptId());
                        tbAssignRecord.setCreateTime(DateUtils.getNowDate());
                        assignRecordPlusMapper.update(tbAssignRecord, new LambdaUpdateWrapper<TbAssignRecord>().
                				set(TbAssignRecord::getLatest, 0).
                				eq(TbAssignRecord::getType, TbAssignRecord.RecordType.CLUES.getValue()).
                				eq(TbAssignRecord::getAssignId, tbAssignRecord.getId()));
//                        assignRecordMapper.updateAssignRecord(tbAssignRecord);
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