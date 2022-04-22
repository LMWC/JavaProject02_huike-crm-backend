package com.huike.business.service.impl;

import java.util.Date;
import java.util.List;

import com.huike.business.strategy.Rule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huike.business.domain.TbBusiness;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.business.mapper.TbBusinessTrackRecordMapper;
import com.huike.business.service.ITbBusinessService;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.service.ITbClueService;
import com.huike.clues.service.ITbRulePoolService;
import com.huike.clues.utils.HuiKeCrmDateUtils;
import com.huike.clues.utils.JobUtils;
import com.huike.common.annotation.DataScope;
import com.huike.common.constant.Constants;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;

/**
 * 商机Service业务层处理
 * @date 2021-04-25
 */
@Service
public class TbBusinessServiceImpl implements ITbBusinessService {

    @Autowired
    private Rule rule;

    @Autowired
    private TbBusinessMapper tbBusinessMapper;

    @Autowired
    private TbAssignRecordMapper tbAssignRecordMapper;

    @Autowired
    private TbBusinessTrackRecordMapper tbBusinessTrackRecordMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    ITbRulePoolService rulePoolService;

    @Autowired
    TbClueMapper tbClueMapper;

    @Autowired
    private ITbClueService tbClueService;

    @Autowired
    TbAssignRecordMapper assignRecordMapper;

    /**
     * 查询商机
     * 
     * @param id 商机ID
     * @return 商机
     */
    @Override
    public TbBusiness selectTbBusinessById(Long id)
    {
        return tbBusinessMapper.selectTbBusinessById(id);
    }

    /**
     * 查询商机列表
     * 
     * @param tbBusiness 商机
     * @return 商机
     */
    @Override
    @DataScope(deptAlias = "r", userAlias = "r")
    public List<TbBusiness> selectTbBusinessList(TbBusiness tbBusiness)
    {
        return tbBusinessMapper.selectTbBusinessList(tbBusiness);
    }


    @Override
    public List<TbBusiness> selectTbBusinessPool(TbBusiness tbBusiness) {
        return tbBusinessMapper.selectTbBusinessPool(tbBusiness);
    }

    /**
     * 新增商机
     * 
     * @param tbBusiness 商机
     * @return 结果
     */
    @Override
    public int insertTbBusiness(TbBusiness tbBusiness)
    {
        tbBusiness.setCreateBy(SecurityUtils.getUsername());
        Date now=DateUtils.getNowDate();
        tbBusiness.setCreateTime(now);
        tbBusinessMapper.insertTbBusiness(tbBusiness);
        TbAssignRecord tbBusinessAssignRecord=new TbAssignRecord();
        tbBusinessAssignRecord.setAssignId(tbBusiness.getId());
        tbBusinessAssignRecord.setUserId(SecurityUtils.getUserId());
        tbBusinessAssignRecord.setUserName(SecurityUtils.getUsername());
        tbBusinessAssignRecord.setDeptId(SecurityUtils.getDeptId());
        tbBusinessAssignRecord.setCreateBy(SecurityUtils.getUsername());
        tbBusinessAssignRecord.setCreateTime(now);
        tbBusinessAssignRecord.setType(Constants.rule_type_business);
        int rows= tbAssignRecordMapper.insertAssignRecord(tbBusinessAssignRecord);
        //修改结束时间
        Date endTime = HuiKeCrmDateUtils.getEndDateByRule(tbBusinessAssignRecord);
        tbBusinessMapper.updateBusinessEndTimeById(tbBusiness.getId(),endTime);

        return rows;
    }

    /**
     * 修改商机
     * 
     * @param tbBusiness 商机
     * @return 结果
     */
    @Override
    public int updateTbBusiness(TbBusiness tbBusiness)
    {
        return tbBusinessMapper.updateTbBusiness(tbBusiness);
    }

    /**
     * 批量删除商机
     * 
     * @param ids 需要删除的商机ID
     * @return 结果
     */
    @Override
    public int deleteTbBusinessByIds(Long[] ids)
    {
        return tbBusinessMapper.deleteTbBusinessByIds(ids);
    }

    /**
     * 删除商机信息
     * 
     * @param id 商机ID
     * @return 结果
     */
    @Override
    public int deleteTbBusinessById(Long id)
    {
        return tbBusinessMapper.deleteTbBusinessById(id);
    }

    @Override
    @Transactional
    public String assign(Long[] businessIds, Long userId) {
        TbRulePool rulePool= rulePoolService.selectTbRulePoolByType(TbRulePool.RuleType.BUSINESS.getValue());
        //统计当前分配人所有线索
        int assignRecords= tbAssignRecordMapper.countAssignBusinessByUser(userId);
        if(assignRecords>=rulePool.getMaxNunmber()){
            return "分配失败！保有量达到上线，最多选择"+rulePool.getMaxNunmber()+"条线索";
        }
        for (int i = 0; i < businessIds.length; i++) {
            Long businessId = businessIds[i];

            //超过最大保有量
            if(assignRecords+i>=rulePool.getMaxNunmber()){
                return "超过当前用户最大保有量，部分分配成功";
            }
            updateStatus(businessId, TbClue.StatusType.UNFOLLOWED.getValue());
            //从新分配
            TbAssignRecord record= addNewRecord(businessId,userId);
            //修改结束时间
            Date endTime = HuiKeCrmDateUtils.getEndDateByRule(record);
            tbBusinessMapper.updateBusinessEndTimeById(businessId,endTime);
        }
        return "全部分配";
    }

    public TbAssignRecord addNewRecord(Long id,Long userId){
        //保留上一条分配记录
        assignRecordMapper.updateLatest(id,TbAssignRecord.RecordType.BUSNIESS.getValue());
        //新建分配记录
        TbAssignRecord tbAssignRecord =new TbAssignRecord();
        tbAssignRecord.setAssignId(id);
        SysUser sysUser = userMapper.selectUserById(userId);
        tbAssignRecord.setUserId(userId);
        tbAssignRecord.setDeptId(sysUser.getDeptId());
        tbAssignRecord.setUserName(sysUser.getUserName());
        Date now=DateUtils.getNowDate();
        tbAssignRecord.setCreateTime(now);
        tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
        tbAssignRecord.setType(TbAssignRecord.RecordType.BUSNIESS.getValue());
        assignRecordMapper.insertAssignRecord(tbAssignRecord);
        return tbAssignRecord;
    }


    @Override
    public String gain(Long[] businessIds, Long userId) {
        boolean isBatch= businessIds.length > 1? true: false;
        TbRulePool rulePool= rulePoolService.selectTbRulePoolByType(TbRulePool.RuleType.BUSINESS.getValue());
        // 统计当前分配人所有线索
        int assignRecords = tbAssignRecordMapper.countAssignBusinessByUser(userId);
        if(assignRecords>=rulePool.getMaxNunmber()){
            throw new CustomException("捞取失败！最大保有量("+rulePool.getMaxNunmber()+")，剩余可以捞取"+(rulePool.getMaxNunmber()-assignRecords)+"条商机");
        }
        for (int i = 0; i < businessIds.length; i++) {
            Long businessId = businessIds[i];

            //超过最大保有量
            if(assignRecords+i>=rulePool.getMaxNunmber()){
                throw  new CustomException("超过最大保有量，部分捞取成功，成功数目"+(i+1)+"条");
            }
            //重复捞取时间限制
            TbAssignRecord businessAssignRecord= tbAssignRecordMapper.selectAssignRecordByAssignId(businessId, TbAssignRecord.RecordType.BUSNIESS.getValue());
            if(businessAssignRecord!=null&&businessAssignRecord.getUserId().equals(userId)){
                Date repeatGetTime = JobUtils.getDate(rulePool.getRepeatGetTime().intValue(),
                        rulePool.getRepeatType(), businessAssignRecord.getCreateTime());
                //捞取限制时间内，不让捞取
                if(DateUtils.getNowDate().before(repeatGetTime)){
                    //批量捞取跳过
                    if(isBatch){
                        continue;
                    }else{
                        throw new CustomException("捞取失败！需要在 "+DateUtils.dateTime(repeatGetTime)+" 后捞取");
                    }
                }
            }
            updateStatus(businessId, TbClue.StatusType.UNFOLLOWED.getValue());
            TbAssignRecord tbAssignRecord = addNewRecord(businessId,userId);
            //修改结束时间
            Date endTime = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
            tbBusinessMapper.updateBusinessEndTimeById(businessId,endTime);
        }
        return "全部捞取成功";
    }


    /**
     * 转商机的方法
     * @param clueId
     * @return
     */
    @Override
    public int changeBusiness(Long clueId) {
        //查询出线索对应的数据
        TbClue tbClue = tbClueMapper.selectTbClueById(clueId);
        //重置状态为转商机
        tbClueMapper.resetNextTimeAndStatus(clueId, TbClue.StatusType.TOBUSINESS.getValue());
        //构建商机对象
        TbBusiness tbBusiness = new TbBusiness();
        BeanUtils.copyProperties(tbClue, tbBusiness);
        tbBusiness.setStatus(TbBusiness.StatusType.UNFOLLOWED.getValue());
        tbBusiness.setClueId(clueId);
        tbBusiness.setNextTime(null);
        tbBusiness.setCreateBy(SecurityUtils.getUsername());
        Date now=DateUtils.getNowDate();
        tbBusiness.setCreateTime(now);
        //添加商机数据
        int rows = tbBusinessMapper.insertTbBusiness(tbBusiness);
        //基于规则来进行分配
        Integer transForBusiness = rule.transforBusiness(tbBusiness);
        if (transForBusiness != 0) {
            return transForBusiness;
        } else {
            return rows;
        }
    }

    /**
     * 修改商机的状态
     * @param clueId
     * @param status
     * @return
     */
    @Override
    public int updateStatus(Long clueId,String status){
        return tbBusinessMapper.resetNextTimeAndStatus(clueId,status);
    }
}
