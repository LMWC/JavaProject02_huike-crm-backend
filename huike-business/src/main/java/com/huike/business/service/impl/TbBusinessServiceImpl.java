package com.huike.business.service.impl;

<<<<<<< HEAD
import java.util.Date;
import java.util.List;

import com.huike.business.strategy.Rule;
=======
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.kie.api.runtime.KieSession;
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.business.domain.TbBusiness;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.business.mapper.TbBusinessTrackRecordMapper;
import com.huike.business.mapper.mybatis_plus.TbBusinessPlusMapper;
import com.huike.business.service.ITbBusinessService;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.mapper.mybatis_plus.SysUserPlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbAssignRecordPlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbCluePlusMapper;
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
 * 
 * @date 2021-04-25
 */
@Service
<<<<<<< HEAD
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
            throw  new CustomException("捞取失败！最大保有量("+rulePool.getMaxNunmber()+")，剩余可以捞取0条线索");
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

    @Override
    public int backPool(Long busniessId,String backReason) {
       TbBusiness business = selectTbBusinessById(busniessId);
       return updateStatus(business.getId(),"3");
    }


    @Override
    public int changeBusiness(Long clueId) {
        TbClue tbClue = tbClueMapper.selectTbClueById(clueId);

        tbClueMapper.resetNextTimeAndStatus(clueId, TbClue.StatusType.TOBUSINESS.getValue());

        TbBusiness tbBusiness = new TbBusiness();
        BeanUtils.copyProperties(tbClue, tbBusiness);
        tbBusiness.setStatus(TbBusiness.StatusType.UNFOLLOWED.getValue());
        tbBusiness.setClueId(clueId);
        tbBusiness.setNextTime(null);
        int rows = this.insertTbBusiness(tbBusiness);

        //默认分配给管理员
        Integer transForBusiness = rule.transforBusiness(tbBusiness);
        if (transForBusiness != 0) {
            return transForBusiness;
        } else {
            return rows;
        }
    }


    @Override
    public int updateStatus(Long clueId,String status){
        return tbBusinessMapper.resetNextTimeAndStatus(clueId,status);
    }
=======
public class TbBusinessServiceImpl extends ServiceImpl<TbBusinessPlusMapper, TbBusiness> implements ITbBusinessService {
	@Autowired
	private TbBusinessMapper tbBusinessMapper;
	
	@Autowired
	private TbBusinessPlusMapper tbBusinessPlusMapper;

	@Autowired
	private TbAssignRecordMapper tbAssignRecordMapper;
	
	@Autowired
	private TbAssignRecordPlusMapper tbAssignRecordPlusMapper;

	@Autowired
	private TbBusinessTrackRecordMapper tbBusinessTrackRecordMapper;

	@Autowired
	private SysUserPlusMapper userMapper;

	@Autowired
	ITbRulePoolService rulePoolService;

	@Autowired
	TbClueMapper tbClueMapper;
	
	@Autowired
	TbCluePlusMapper tbCluePlusMapper;

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
	public TbBusiness selectTbBusinessById(Long id) {
		TbBusiness data = tbBusinessPlusMapper.selectById(id);
		return data;
	}

	/**
	 * 查询商机列表
	 * 
	 * @param tbBusiness 商机
	 * @return 商机
	 */
	@Override
	@DataScope(deptAlias = "r", userAlias = "r")
	public List<TbBusiness> selectTbBusinessList(TbBusiness tbBusiness) {
		
		return tbBusinessMapper.selectTbBusinessList(tbBusiness);
	}

	@Override
	public List<TbBusiness> selectTbBusinessPool(TbBusiness tbBusiness) {
		LambdaQueryWrapper<TbBusiness> queryWrapper = getTbBusinessQueryWapper(tbBusiness);
		queryWrapper.orderByDesc(TbBusiness::getCreateTime);
		return tbBusinessPlusMapper.selectList(queryWrapper);
	}

	/**
	 * 新增商机
	 * 
	 * @param tbBusiness 商机
	 * @return 结果
	 */
	@Override
	public int insertTbBusiness(TbBusiness tbBusiness) {
		tbBusiness.setCreateBy(SecurityUtils.getUsername());
		Date now = DateUtils.getNowDate();
		tbBusiness.setCreateTime(now);
		this.save(tbBusiness);
//		tbBusinessPlusMapper.insert(tbBusiness);
		TbAssignRecord tbBusinessAssignRecord = new TbAssignRecord();
		tbBusinessAssignRecord.setAssignId(tbBusiness.getId());
		tbBusinessAssignRecord.setUserId(SecurityUtils.getUserId());
		tbBusinessAssignRecord.setUserName(SecurityUtils.getUsername());
		tbBusinessAssignRecord.setDeptId(SecurityUtils.getDeptId());
		tbBusinessAssignRecord.setCreateBy(SecurityUtils.getUsername());
		tbBusinessAssignRecord.setCreateTime(now);
		tbBusinessAssignRecord.setType(Constants.rule_type_business);
		int rows = tbAssignRecordPlusMapper.insert(tbBusinessAssignRecord);
		// 修改结束时间
		Date endTime = HuiKeCrmDateUtils.getEndDateByRule(tbBusinessAssignRecord);
		LambdaUpdateWrapper<TbBusiness> updateWrapper = new LambdaUpdateWrapper<TbBusiness>();
		// update tb_business set end_time=#{endTime},next_time = null where id = #{id}
		updateWrapper.eq(TbBusiness::getId,tbBusiness.getId());
		updateWrapper.set(TbBusiness::getEndTime,endTime);
		updateWrapper.set(TbBusiness::getNextTime,null);
		tbBusinessPlusMapper.update(tbBusiness,updateWrapper);
//		tbBusinessMapper.updateBusinessEndTimeById(tbBusiness.getId(), endTime);
		return rows;
	}

	/**
	 * 修改商机
	 * 
	 * @param tbBusiness 商机
	 * @return 结果
	 */
	@Override
	public int updateTbBusiness(TbBusiness tbBusiness) {
		LambdaUpdateWrapper<TbBusiness> updateWrapper = getUpdateWrapper(tbBusiness);
		return tbBusinessPlusMapper.update(tbBusiness, updateWrapper);
//		return tbBusinessMapper.updateTbBusiness(tbBusiness);
	}

	/**
	 * 批量删除商机
	 * 
	 * @param ids 需要删除的商机ID
	 * @return 结果
	 */
	@Override
	public int deleteTbBusinessByIds(Long[] ids) {
		LambdaQueryWrapper<TbBusiness> queryWrapper = new LambdaQueryWrapper<TbBusiness>();
		queryWrapper.in(TbBusiness::getId, ids);
		return tbBusinessPlusMapper.delete(queryWrapper);
//		return tbBusinessMapper.deleteTbBusinessByIds(ids);
	}

	/**
	 * 删除商机信息
	 * 
	 * @param id 商机ID
	 * @return 结果
	 */
	@Override
	public int deleteTbBusinessById(Long id) {
		LambdaQueryWrapper<TbBusiness> queryWrapper = new LambdaQueryWrapper<TbBusiness>();
		queryWrapper.eq(TbBusiness::getId, id);
		return tbBusinessPlusMapper.delete(queryWrapper);
//		return tbBusinessMapper.deleteTbBusinessById(id);
	}

	@Override
	@Transactional
	public String assign(Long[] businessIds, Long userId) {
		TbRulePool rulePool = rulePoolService.selectTbRulePoolByType(TbRulePool.RuleType.BUSINESS.getValue());
		// 统计当前分配人所有线索
		int assignRecords = tbAssignRecordMapper.countAssignBusinessByUser(userId);
		if (assignRecords >= rulePool.getMaxNunmber()) {
			return "分配失败！保有量达到上线，最多选择" + rulePool.getMaxNunmber() + "条线索";
		}
		for (int i = 0; i < businessIds.length; i++) {
			Long businessId = businessIds[i];

			// 超过最大保有量
			if (assignRecords + i >= rulePool.getMaxNunmber()) {
				return "超过当前用户最大保有量，部分分配成功";
			}
			updateStatus(businessId, TbClue.StatusType.UNFOLLOWED.getValue());
			// 从新分配
			TbAssignRecord record = addNewRecord(businessId, userId);
			// 修改结束时间
			Date endTime = HuiKeCrmDateUtils.getEndDateByRule(record);
			tbBusinessMapper.updateBusinessEndTimeById(businessId, endTime);
		}
		return "全部分配";
	}

	public TbAssignRecord addNewRecord(Long id, Long userId) {
		// 保留上一条分配记录
		assignRecordMapper.updateLatest(id, TbAssignRecord.RecordType.BUSNIESS.getValue());
		// 新建分配记录
		TbAssignRecord tbAssignRecord = new TbAssignRecord();
		tbAssignRecord.setAssignId(id);
		SysUser sysUser = userMapper.selectById(userId);
		tbAssignRecord.setUserId(userId);
		tbAssignRecord.setDeptId(sysUser.getDeptId());
		tbAssignRecord.setUserName(sysUser.getUserName());
		Date now = DateUtils.getNowDate();
		tbAssignRecord.setCreateTime(now);
		tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
		tbAssignRecord.setType(TbAssignRecord.RecordType.BUSNIESS.getValue());
		tbAssignRecordPlusMapper.insert(tbAssignRecord);
		return tbAssignRecord;
	}

	@Override
	public String gain(Long[] businessIds, Long userId) {
		boolean isBatch = businessIds.length > 1 ? true : false;
		TbRulePool rulePool = rulePoolService.selectTbRulePoolByType(TbRulePool.RuleType.BUSINESS.getValue());
		// 统计当前分配人所有线索
		int assignRecords = tbAssignRecordMapper.countAssignBusinessByUser(userId);
		if (assignRecords >= rulePool.getMaxNunmber()) {
			throw new CustomException("捞取失败！最大保有量(" + rulePool.getMaxNunmber() + ")，剩余可以捞取"
					+ (rulePool.getMaxNunmber() - assignRecords) + "条线索");
		}
		for (int i = 0; i < businessIds.length; i++) {
			Long businessId = businessIds[i];

			// 超过最大保有量
			if (assignRecords + i >= rulePool.getMaxNunmber()) {
				throw new CustomException("超过最大保有量，部分捞取成功，成功数目" + (i + 1) + "条");
			}
			// 重复捞取时间限制
			TbAssignRecord businessAssignRecord = tbAssignRecordMapper.selectAssignRecordByAssignId(businessId,
					TbAssignRecord.RecordType.BUSNIESS.getValue());
			if (businessAssignRecord != null && businessAssignRecord.getUserId().equals(userId)) {
				Date repeatGetTime = JobUtils.getDate(rulePool.getRepeatGetTime().intValue(), rulePool.getRepeatType(),
						businessAssignRecord.getCreateTime());
				// 捞取限制时间内，不让捞取
				if (DateUtils.getNowDate().before(repeatGetTime)) {
					// 批量捞取跳过
					if (isBatch) {
						continue;
					} else {
						throw new CustomException("捞取失败！需要在 " + DateUtils.dateTime(repeatGetTime) + " 后捞取");
					}
				}
			}
			updateStatus(businessId, TbClue.StatusType.UNFOLLOWED.getValue());
			TbAssignRecord tbAssignRecord = addNewRecord(businessId, userId);
			// 修改结束时间
			Date endTime = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
			tbBusinessMapper.updateBusinessEndTimeById(businessId, endTime);
		}
		return "全部捞取成功";
	}

	@Override
	public int backPool(Long busniessId, String backReason) {
		TbBusiness business = selectTbBusinessById(busniessId);
		return updateStatus(business.getId(), "3");
	}

	@Override
	public int changeBusiness(Long clueId) {
		TbClue tbClue = tbCluePlusMapper.selectOne(new LambdaQueryWrapper<TbClue>().eq(TbClue::getId, clueId));
		// update tb_clue set next_time=null, status=#{status} where id = #{id}
		LambdaUpdateWrapper<TbClue> updateWrapper = new LambdaUpdateWrapper<TbClue>();
		updateWrapper.eq(TbClue::getId, clueId);
		updateWrapper.set(TbClue::getNextTime, null);
		updateWrapper.set(TbClue::getStatus, TbClue.StatusType.TOBUSINESS.getValue());
		tbCluePlusMapper.update(tbClue, updateWrapper);
//		tbClueMapper.resetNextTimeAndStatus(clueId, TbClue.StatusType.TOBUSINESS.getValue());

		TbBusiness tbBusiness = new TbBusiness();
		BeanUtils.copyProperties(tbClue, tbBusiness);
		tbBusiness.setStatus(TbBusiness.StatusType.UNFOLLOWED.getValue());
		tbBusiness.setClueId(clueId);
		tbBusiness.setNextTime(null);
		int rows = this.insertTbBusiness(tbBusiness);

		// 默认分配给管理员
		if (!SecurityUtils.getLoginUser().getUsername().equals("admin")) {
			TbAssignRecord tbAssignRecord = new TbAssignRecord();
			tbAssignRecord.setAssignId(tbBusiness.getId());
			tbAssignRecord.setUserId(SecurityUtils.getAdmin());
			tbAssignRecord.setUserName("admin");
			// tbAssignRecord.setDeptId(SecurityUtils.);
			tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
			tbAssignRecord.setCreateTime(DateUtils.getNowDate());
			tbAssignRecord.setType(TbAssignRecord.RecordType.BUSNIESS.getValue());
			tbBusiness.setNextTime(null);
			rows = tbAssignRecordPlusMapper.insert(tbAssignRecord);
		}
		// 规则分配支持
		KieSession session = RuleUtils.getRuleSession(Constants.rule_type_business);
		if (session != null) {
			session.insert(tbBusiness);
			session.fireAllRules();
			session.destroy();
		}
		return rows;
	}

	@Override
	public int updateStatus(Long clueId, String status) {
		return tbBusinessMapper.resetNextTimeAndStatus(clueId, status);
	}
	
	/**
	 * 获取商机查询条件
	 * @param tbBusiness
	 * @return
	 */
	private LambdaQueryWrapper<TbBusiness> getTbBusinessQueryWapper(TbBusiness tbBusiness) {
		LambdaQueryWrapper<TbBusiness> queryWrapper = new LambdaQueryWrapper<>();
		//模糊查询Id
		queryWrapper.like((tbBusiness.getId()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getId()+"")),
				TbBusiness::getId, tbBusiness.getId());
		//模糊查询姓名
		queryWrapper.like((tbBusiness.getName()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getName().toString())),
				TbBusiness::getName, tbBusiness.getName());
		//模糊查询手机号
		queryWrapper.like((tbBusiness.getPhone()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getPhone()+"")),
				TbBusiness::getPhone, tbBusiness.getPhone());
		//渠道
		queryWrapper.eq((tbBusiness.getChannel()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getChannel()+"")),
				TbBusiness::getChannel, tbBusiness.getChannel());
		//活动ID
		queryWrapper.eq((tbBusiness.getActivityId()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getActivityId()+"")),
				TbBusiness::getActivityId, tbBusiness.getActivityId());
		//获取省份
		queryWrapper.eq((tbBusiness.getProvinces()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getProvinces()+"")),
				TbBusiness::getProvinces, tbBusiness.getProvinces());
		//城市
		queryWrapper.eq((tbBusiness.getCity()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getCity()+"")),
				TbBusiness::getCity, tbBusiness.getCity());
		
		//性别
		queryWrapper.eq((tbBusiness.getSex()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getSex()+"")),
				TbBusiness::getSex, tbBusiness.getSex());
		
		//年龄
		queryWrapper.eq((tbBusiness.getAge()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getAge()+"")),
				TbBusiness::getAge, tbBusiness.getAge());
		//weixin
		queryWrapper.eq((tbBusiness.getWeixin()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getWeixin()+"")),
				TbBusiness::getWeixin, tbBusiness.getWeixin());
		//qq
		queryWrapper.eq((tbBusiness.getQq()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getQq()+"")),
				TbBusiness::getQq, tbBusiness.getQq());
		//level
		queryWrapper.eq((tbBusiness.getLevel()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getLevel()+"")),
				TbBusiness::getLevel, tbBusiness.getLevel());
		//subject
		queryWrapper.eq((tbBusiness.getSubject()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getSubject()+"")),
				TbBusiness::getSubject, tbBusiness.getSubject());
		//courseId
		queryWrapper.eq((tbBusiness.getCourseId()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getCourseId()+"")),
				TbBusiness::getCourseId, tbBusiness.getCourseId());
		//occupation
		queryWrapper.eq((tbBusiness.getOccupation()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getOccupation()+"")),
				TbBusiness::getOccupation, tbBusiness.getOccupation());
		//education
		queryWrapper.eq((tbBusiness.getEducation()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getEducation()+"")),
				TbBusiness::getEducation, tbBusiness.getEducation());
		//job
		queryWrapper.eq((tbBusiness.getJob()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getJob()+"")),
				TbBusiness::getJob, tbBusiness.getJob());
		//salary
		queryWrapper.eq((tbBusiness.getSalary()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getSalary()+"")),
				TbBusiness::getSalary, tbBusiness.getSalary());
		//major
		queryWrapper.eq((tbBusiness.getMajor()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getMajor()+"")),
				TbBusiness::getMajor, tbBusiness.getMajor());
		//expectedSalary
		queryWrapper.eq((tbBusiness.getExpectedSalary()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getExpectedSalary()+"")),
				TbBusiness::getExpectedSalary, tbBusiness.getExpectedSalary());
		//reasons
		queryWrapper.eq((tbBusiness.getReasons()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getReasons()+"")),
				TbBusiness::getReasons, tbBusiness.getReasons());
		//plan
		queryWrapper.eq((tbBusiness.getPlan()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getPlan()+"")),
				TbBusiness::getPlan, tbBusiness.getPlan());
		//planTime
		queryWrapper.eq((tbBusiness.getPlanTime()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getPlanTime()+"")),
				TbBusiness::getPlanTime, tbBusiness.getPlanTime());
		//otherIntention
		queryWrapper.eq((tbBusiness.getOtherIntention()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getOtherIntention()+"")),
				TbBusiness::getOtherIntention, tbBusiness.getOtherIntention());
		//nextTime
		queryWrapper.eq((tbBusiness.getNextTime()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getNextTime()+"")),
				TbBusiness::getNextTime, tbBusiness.getNextTime());
		//beginCreateTime
		queryWrapper.between((tbBusiness.getBeginCreateTime()!=null&&
				StringUtils.isNotEmpty(tbBusiness.getBeginCreateTime()+"")),TbBusiness::getCreateTime,
						tbBusiness.getBeginCreateTime(), tbBusiness.getEndCreateTime());
		//status
		if((tbBusiness.getStatus()!=null)&&(StringUtils.isNotEmpty(tbBusiness.getStatus()+""))){
			queryWrapper.eq(TbBusiness::getStatus, tbBusiness.getStatus());
		}else if((tbBusiness.getStatus()==null)&&(StringUtils.isEmpty(tbBusiness.getStatus()+""))){
			queryWrapper.in(TbBusiness::getStatus, 
					new ArrayList<String>(){{
			            add("1");
			            add("2");
					}}
			);
		}
		return queryWrapper;
	}

	/**
	 * 获取修改条件
	 * @param tbBusiness
	 * @return
	 */
	private LambdaUpdateWrapper<TbBusiness> getUpdateWrapper(TbBusiness tbBusiness) {
		LambdaUpdateWrapper<TbBusiness> updateWrapper = new LambdaUpdateWrapper<TbBusiness>();
		updateWrapper.eq(TbBusiness::getId, tbBusiness.getId());
		//name
		updateWrapper.set((tbBusiness.getName()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getName()+"")),
				TbBusiness::getName,tbBusiness.getName());
		//phone
		updateWrapper.set((tbBusiness.getPhone()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getPhone()+"")),
				TbBusiness::getPhone,tbBusiness.getPhone());
		//channel
		updateWrapper.set((tbBusiness.getChannel()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getChannel()+"")),
				TbBusiness::getChannel,tbBusiness.getChannel());
		//activityId
		updateWrapper.set((tbBusiness.getActivityId()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getActivityId()+"")),
				TbBusiness::getActivityId,tbBusiness.getActivityId());
		//provinces
		updateWrapper.set((tbBusiness.getProvinces()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getProvinces()+"")),
				TbBusiness::getProvinces,tbBusiness.getProvinces());
		//city
		updateWrapper.set((tbBusiness.getCity()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getCity()+"")),
				TbBusiness::getCity,tbBusiness.getCity());
		//sex
		updateWrapper.set((tbBusiness.getSex()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getSex()+"")),
				TbBusiness::getSex,tbBusiness.getSex());
		//age
		updateWrapper.set((tbBusiness.getAge()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getAge()+"")),
				TbBusiness::getAge,tbBusiness.getAge());
		//weixin
		updateWrapper.set((tbBusiness.getWeixin()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getWeixin()+"")),
				TbBusiness::getWeixin,tbBusiness.getWeixin());
		//QQ
		updateWrapper.set((tbBusiness.getQq()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getQq()+"")),
				TbBusiness::getQq,tbBusiness.getQq());
		//level
		updateWrapper.set((tbBusiness.getLevel()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getLevel()+"")),
				TbBusiness::getLevel,tbBusiness.getLevel());
		//subject
		updateWrapper.set((tbBusiness.getSubject()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getSubject()+"")),
				TbBusiness::getSubject,tbBusiness.getSubject());
		//courseId
		updateWrapper.set((tbBusiness.getCourseId()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getCourseId()+"")),
				TbBusiness::getCourseId,tbBusiness.getCourseId());
		//createBy
		updateWrapper.set((tbBusiness.getCreateBy()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getCreateBy()+"")),
				TbBusiness::getCreateBy,tbBusiness.getCreateBy());
		//createTime
		updateWrapper.set((tbBusiness.getCreateTime()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getCreateTime()+"")),
				TbBusiness::getCreateTime,tbBusiness.getCreateTime());
		//occupation
		updateWrapper.set((tbBusiness.getOccupation()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getOccupation()+"")),
				TbBusiness::getOccupation,tbBusiness.getOccupation());
		//education
		updateWrapper.set((tbBusiness.getEducation()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getEducation()+"")),
				TbBusiness::getEducation,tbBusiness.getEducation());
		//job
		updateWrapper.set((tbBusiness.getJob()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getJob()+"")),
				TbBusiness::getJob,tbBusiness.getJob());
		//salary
		updateWrapper.set((tbBusiness.getSalary()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getSalary()+"")),
				TbBusiness::getSalary,tbBusiness.getSalary());
		//major
		updateWrapper.set((tbBusiness.getMajor()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getMajor()+"")),
				TbBusiness::getMajor,tbBusiness.getMajor());
		//expectedSalary
		updateWrapper.set((tbBusiness.getExpectedSalary()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getExpectedSalary()+"")),
				TbBusiness::getExpectedSalary,tbBusiness.getExpectedSalary());
		//reasons
		updateWrapper.set((tbBusiness.getReasons()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getReasons()+"")),
				TbBusiness::getReasons,tbBusiness.getReasons());
		//plan
		updateWrapper.set((tbBusiness.getPlan()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getPlan()+"")),
				TbBusiness::getPlan,tbBusiness.getPlan());
		//planTime
		updateWrapper.set((tbBusiness.getPlanTime()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getPlanTime()+"")),
				TbBusiness::getPlanTime,tbBusiness.getPlanTime());
		//otherIntention
		updateWrapper.set((tbBusiness.getOtherIntention()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getOtherIntention()+"")),
				TbBusiness::getOtherIntention,tbBusiness.getOtherIntention());
		//nextTime
		updateWrapper.set((tbBusiness.getNextTime()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getNextTime()+"")),
				TbBusiness::getNextTime,tbBusiness.getNextTime());
		//status
		updateWrapper.set((tbBusiness.getStatus()!=null)&&
				(StringUtils.isNotEmpty(tbBusiness.getStatus()+"")),
				TbBusiness::getStatus,tbBusiness.getStatus());
		return updateWrapper;
	}

>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
}
