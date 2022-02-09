package com.huike.clues.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.huike.clues.strategy.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.domain.vo.TbClueExcelVo;
import com.huike.clues.dto.TbClueDto;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.mapper.TbClueTrackRecordMapper;
import com.huike.clues.mapper.mybatis_plus.SysDictDataPlusMapper;
import com.huike.clues.mapper.mybatis_plus.SysUserPlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbAssignRecordPlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbCluePlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbClueTrackRecordPlusMapper;
import com.huike.clues.service.ITbActivityService;
import com.huike.clues.service.ITbClueService;
import com.huike.clues.service.ITbRulePoolService;
import com.huike.clues.utils.HuiKeCrmDateUtils;
import com.huike.clues.utils.JobUtils;
import com.huike.common.annotation.DataScope;
import com.huike.common.constant.Constants;
import com.huike.common.core.domain.entity.SysDictData;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.StringUtils;
import com.huike.common.utils.bean.BeanUtils;

/**
 * 线索管理Service业务层处理
 * 
 * @date 2021-04-02
 */
@Service
<<<<<<< HEAD
public class TbClueServiceImpl implements ITbClueService {

	@Autowired
	private Rule rule;

=======
public class TbClueServiceImpl extends ServiceImpl<TbCluePlusMapper,TbClue> implements ITbClueService {
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
	@Autowired
	private TbClueMapper tbClueMapper;

	@Autowired
	private TbAssignRecordMapper assignRecordMapper;
	
	@Autowired
	private TbAssignRecordPlusMapper assignRecordPlusMapper;

	@Autowired
	private TbClueTrackRecordMapper tbClueTrackRecordMapper;
	
	@Autowired
	private TbClueTrackRecordPlusMapper tbClueTrackRecordPlusMapper;

	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private SysUserPlusMapper userPlusMapper;

	@Autowired
	ITbRulePoolService rulePoolService;

	@Autowired
	SysDictDataMapper sysDictDataMapper;
	
	@Autowired
	SysDictDataPlusMapper sysDictDataPlusMapper;
	
	@Autowired
	private TbActivityMapper tbActivityMapper;

	@Autowired
	private ITbActivityService activityService;

	@Autowired
	private ITbClueService tbClueService;

	/**
	 * 查询线索管理
	 * 
	 * @param id 线索管理ID
	 * @return 线索管理
	 */
	@Override
	public TbClue selectTbClueById(Long id) {
		LambdaQueryWrapper<TbClue> queryWrapper = new LambdaQueryWrapper<TbClue>();
		queryWrapper.eq(TbClue::getId, id);
		TbClue tbClue = this.getOne(queryWrapper);
		if (tbClue != null && tbClue.getActivityId() != null) {
			TbActivity activity = tbActivityMapper.selectTbActivityById(tbClue.getActivityId());
			if (activity != null) {
				tbClue.setActivityInfo(activity.getCode() + ":" + activity.getName() + ":" + activity.getInfo());
			}
		}
		// 查询转派信息
		//where assign_id = #{assignId} and type = #{type} and latest='1' ORDER BY create_time DESC LIMIT 1
		LambdaQueryWrapper<TbAssignRecord> lambdaQueryWrapper = new LambdaQueryWrapper<TbAssignRecord>();
		lambdaQueryWrapper.eq(TbAssignRecord::getAssignId, tbClue.getId());
		lambdaQueryWrapper.eq(TbAssignRecord::getType, "0");
		lambdaQueryWrapper.eq(TbAssignRecord::getLatest, 1);
		lambdaQueryWrapper.orderByDesc(TbAssignRecord::getCreateTime).last("limit 1");
		TbAssignRecord selectList = assignRecordPlusMapper.selectOne(lambdaQueryWrapper);
		tbClue.setOwner(selectList.getUserName());
		tbClue.setOwnerTime(selectList.getCreateTime());
		return tbClue;
	}

	/**
	 * 查询线索管理列表
	 * 
	 * @param tbClue 线索管理
	 * @return 线索管理
	 */
	@Override
	@DataScope(deptAlias = "r", userAlias = "r")
	public List<TbClue> selectTbClueList(TbClue tbClue) {
		return tbClueMapper.selectTbClueList(tbClue);
	}

	@Override
	public List<TbClue> selectTbCluePool(TbClue tbClue) {
		List<TbClue> clueList = tbClueMapper.selectTbCluePoll(tbClue);
		clueList.forEach(d -> {
			if (d.getActivityId() != null) {
				TbActivity activity = tbActivityMapper.selectTbActivityById(d.getActivityId());
				if (activity != null) {
					d.setActivityName(activity.getName());
				}
			}
		});
		return clueList;
	}

	/**
	 * 新增线索管理
	 * 
	 * @param tbClue 线索管理
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertTbClue(TbClue tbClue) {
		tbClue.setCreateBy(SecurityUtils.getUsername());
		Date now = DateUtils.getNowDate();
		tbClue.setCreateTime(now);
		this.save(tbClue);
 		TbAssignRecord tbAssignRecord = new TbAssignRecord();
		tbAssignRecord.setAssignId(tbClue.getId());
		tbAssignRecord.setUserId(SecurityUtils.getUserId());
		tbAssignRecord.setUserName(SecurityUtils.getUsername());
		tbAssignRecord.setDeptId(SecurityUtils.getDeptId());
		tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
		tbAssignRecord.setCreateTime(now);
		int rows = assignRecordPlusMapper.insert(tbAssignRecord);
		// 线索池规则支持
		Date endDate = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
		
		this.update(new LambdaUpdateWrapper<TbClue>().eq(TbClue::getId, tbClue.getId()).
				set(TbClue::getEndTime, endDate).
				set(TbClue::getNextTime, null));
//		tbClueMapper.updateClueEndTimeById(tbClue.getId(), endDate);
		return rows;
	}

	/**
	 * 修改线索管理
	 * update tb_clue
        <trim prefix="SET" suffixOverrides=",">
            <if test="name != null">name = #{name},</if>
            <if test="phone != null and phone != ''">phone = #{phone},</if>
            <if test="channel != null and channel != ''">channel = #{channel},</if>
            <if test="activityId != null">activity_id = #{activityId},</if>
            <if test="sex != null">sex = #{sex},</if>
            <if test="age != null">age = #{age},</if>
            <if test="weixin != null">weixin = #{weixin},</if>
            <if test="qq != null">qq = #{qq},</if>
            <if test="level != null">level = #{level},</if>
            <if test="subject != null">subject = #{subject},</if>
            <if test="status != null">status = #{status},</if>
            <if test="owner != null">owner = #{owner},</if>
            <if test="createTime != null">create_time = #{createTime},</if>
            <if test="createBy != null">create_by = #{createBy},</if>
            <if test="assignBy != null">assign_by = #{assignBy},</if>
            <if test="assignTime != null">assign_time = #{assignTime},</if>
            <if test="falseCount != null">false_count = #{falseCount},</if>
            <if test="nextTime != null">next_time = #{nextTime},</if>
        </trim>
        where id = #{id}
	 * @param tbClue 线索管理
	 * @return 结果
	 */
	@Override
	public int updateTbClue(TbClue tbClue) {
		LambdaUpdateWrapper<TbClue> updateWrapper = new LambdaUpdateWrapper<TbClue>();
		updateWrapper.eq(TbClue::getId, tbClue.getId());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getName()),TbClue::getName, tbClue.getName());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getPhone()),TbClue::getPhone, tbClue.getPhone());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getChannel()),TbClue::getChannel, tbClue.getChannel());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getActivityId()+""),TbClue::getActivityId, tbClue.getActivityId());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getSex()),TbClue::getSex, tbClue.getSex());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getAge()+""),TbClue::getAge, tbClue.getAge());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getWeixin()),TbClue::getWeixin, tbClue.getWeixin());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getQq()),TbClue::getQq, tbClue.getQq());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getLevel()),TbClue::getLevel, tbClue.getLevel());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getSubject()),TbClue::getSubject, tbClue.getSubject());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getStatus()),TbClue::getStatus, tbClue.getStatus());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getOwner()),TbClue::getOwner, tbClue.getOwner());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getStatus()),TbClue::getStatus, tbClue.getStatus());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getStatus()),TbClue::getStatus, tbClue.getStatus());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getStatus()),TbClue::getStatus, tbClue.getStatus());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getStatus()),TbClue::getStatus, tbClue.getStatus());
		return this.update(updateWrapper)&&true ? 1:0;
//		return tbClueMapper.updateTbClue(tbClue);
	}

	/**
	 * 批量删除线索管理
	 * 
	 * @param ids 需要删除的线索管理ID
	 * @return 结果
	 */
	@Override
	public int deleteTbClueByIds(Long[] ids) {
		return this.remove(new LambdaQueryWrapper<TbClue>().in(TbClue::getId, ids))&&true?ids.length:0;
//		return tbClueMapper.deleteTbClueByIds(ids);
	}

	/**
	 * 删除线索管理信息
	 * 
	 * @param id 线索管理ID
	 * @return 结果
	 */
	@Override
	public int deleteTbClueById(Long id) {
		return this.remove(new LambdaQueryWrapper<TbClue>().eq(TbClue::getId, id))&&true?1:0;
//		return tbClueMapper.deleteTbClueById(id);
	}

	@Override
	@Transactional
	public Map<String, Integer> importClues(List<TbClue> clueList) {
		if (StringUtils.isNull(clueList) || clueList.size() == 0) {
			throw new CustomException("导入用户数据不能为空！");
		}
		Map<String, Integer> map = new HashMap<>();
		List<TbClue> toAssignlist = new ArrayList<>();
		int successNum = 0;
		int failureNum = 0;
		for (TbClue clue : clueList) {
			try {
				if (StringUtils.isBlank(clue.getPhone())) {
					failureNum++;
					continue;
				}
				if (StringUtils.isBlank(clue.getChannel())) {
					failureNum++;
					continue;
				}

				// 验证是否存在这个用户
				TbClue dbcule = this.getOne(new LambdaQueryWrapper<TbClue>().eq(TbClue::getPhone, clue.getPhone()));
				if (dbcule == null) {
					// 特殊字段处理
					//select dict_value from sys_dict_data where dict_type = #{dictType} and dict_label = #{dictLabel}
					String channel = sysDictDataPlusMapper.selectOne(new LambdaQueryWrapper<SysDictData>().
							eq(SysDictData::getDictType, TbClue.ImportDictType.CHANNEL.getDictType()).
							eq(SysDictData::getDictLabel, clue.getChannel())).getDictValue();
//					String channel = sysDictDataMapper.selectDictValue(TbClue.ImportDictType.CHANNEL.getDictType(),
//							clue.getChannel());
					clue.setChannel(channel);

					if (StringUtils.isNoneBlank(clue.getSubject())) {
						String subject = sysDictDataPlusMapper.selectOne(new LambdaQueryWrapper<SysDictData>().
								eq(SysDictData::getDictType, TbClue.ImportDictType.SUBJECT.getDictType()).
								eq(SysDictData::getDictLabel,clue.getSubject())).getDictValue();
//						String subject = sysDictDataMapper.selectDictValue(TbClue.ImportDictType.SUBJECT.getDictType(),
//								clue.getSubject());
						clue.setSubject(subject);
					}

					if (StringUtils.isNoneBlank(clue.getLevel())) {
						String level = sysDictDataPlusMapper.selectOne(new LambdaQueryWrapper<SysDictData>().
								eq(SysDictData::getDictType, TbClue.ImportDictType.LEVEL.getDictType()).
								eq(SysDictData::getDictLabel,clue.getLevel())).getDictValue();
//						String level = sysDictDataMapper.selectDictValue(TbClue.ImportDictType.LEVEL.getDictType(),
//								clue.getLevel());
						clue.setLevel(level);
					}

					if (StringUtils.isNoneBlank(clue.getSex())) {
						String sex = sysDictDataPlusMapper.selectOne(new LambdaQueryWrapper<SysDictData>().
								eq(SysDictData::getDictType, TbClue.ImportDictType.SEX.getDictType()).
								eq(SysDictData::getDictLabel,clue.getSex())).getDictValue();
//						String sex = sysDictDataMapper.selectDictValue(TbClue.ImportDictType.SEX.getDictType(),
//								clue.getSex());
						clue.setSex(sex);
					}

					if (StringUtils.isNoneBlank(clue.getActivityName())) {
						String sex = sysDictDataPlusMapper.selectOne(new LambdaQueryWrapper<SysDictData>().
								eq(SysDictData::getDictType, TbClue.ImportDictType.SEX.getDictType()).
								eq(SysDictData::getDictLabel,clue.getSex())).getDictValue();
//						String sex = sysDictDataMapper.selectDictValue(TbClue.ImportDictType.SEX.getDictType(),
//								clue.getSex());
						clue.setSex(sex);
					}
					clue.setStatus(TbClue.StatusType.UNFOLLOWED.getValue());
					tbClueMapper.insertTbClue(clue);
					// 默认分配超级管理员
<<<<<<< HEAD
					//如果线索添加成功，利用策略将线索分配给具体的人
					rule.loadRule(clue);
=======
					TbAssignRecord tbAssignRecord = new TbAssignRecord();
					tbAssignRecord.setAssignId(clue.getId());

					tbAssignRecord.setUserId(SecurityUtils.getAdmin());
					tbAssignRecord.setUserName("admin");
					tbAssignRecord.setDeptId(SecurityUtils.getDeptId());
					tbAssignRecord.setCreateBy("admin");
					tbAssignRecord.setCreateTime(clue.getCreateTime());
					assignRecordPlusMapper.insert(tbAssignRecord);
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
					successNum++;
					toAssignlist.add(clue);
				} else {
					failureNum++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				failureNum++;
			}
		}

		map.put("successNum", successNum);
		map.put("failureNum", failureNum);
		return map;
	}

	@Override
	public String assign(Long[] clueIds, Long userId) {
		TbRulePool rulePool = rulePoolService.selectTbRulePoolByType(Constants.rule_type_clue);
		// TbAssignRecord tbAssignRecord =new TbAssignRecord();
		// 统计当前分配人所有线索  简单的连表查询
		int assignRecords = assignRecordMapper.countAssignCluesByUser(userId);
		if (assignRecords >= rulePool.getMaxNunmber()) {
			throw new CustomException("分配失败！最大保有量(" + rulePool.getMaxNunmber() + ")，剩余可以分配"
					+ (rulePool.getMaxNunmber() - assignRecords) + "条线索");
		}
		for (int i = 0; i < clueIds.length; i++) {
			Long clueId = clueIds[i];
			// 超过最大保有量
			if (assignRecords + i >= rulePool.getMaxNunmber()) {
				// return "超过当前用户最大保有量，部分分配成功";
				throw new CustomException("分配失败！保有量达到上线，最多选择" + rulePool.getMaxNunmber() + "条线索");
			}

			// 从新分配
			updateStatus(clueId, TbClue.StatusType.UNFOLLOWED.getValue());
			TbAssignRecord tbAssignRecord = addNewRecord(clueId, userId);
			Date endDate = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
			//update tb_clue set end_time=#{endTime},next_time = null where id = #{id}
			this.update(new LambdaUpdateWrapper<TbClue>().eq(TbClue::getId, clueId).
					set(TbClue::getEndTime, endDate).
					set(TbClue::getNextTime, null));
//			tbClueMapper.updateClueEndTimeById(clueId, endDate);
		}
		return "全部分配";
	}

	// 重复捞取时间限制
	@Override
	public String gain(Long[] clueIds, Long userId) {
		// 是否批量捞取
		boolean isBatch = clueIds.length > 1 ? true : false;
		TbRulePool rulePool = rulePoolService.selectTbRulePoolByType(TbRulePool.RuleType.CLUES.getValue());
		// 统计当前分配人所有线索
		int asignRecords = assignRecordMapper.countAssignCluesByUser(userId);
		if (asignRecords >= rulePool.getMaxNunmber()) {
			throw new CustomException("捞取失败！最大保有量(" + rulePool.getMaxNunmber() + ")，剩余可以捞取0条线索");
		}
		for (int i = 0; i < clueIds.length; i++) {
			Long clueId = clueIds[i];

			// 超过最大保有量
			if (asignRecords + i >= rulePool.getMaxNunmber()) {
				throw new CustomException("捞取失败！保有量达到上线，最多选择" + rulePool.getMaxNunmber() + "条线索");
			}
			// 最近捞取记录
			//where assign_id = #{assignId} and type = #{type} and latest='1' ORDER BY create_time DESC LIMIT 1
			TbAssignRecord assignRecord = assignRecordPlusMapper.selectOne(new LambdaQueryWrapper<TbAssignRecord>().
					eq(TbAssignRecord::getAssignId, clueId).
					eq(TbAssignRecord::getType, TbAssignRecord.RecordType.CLUES.getValue()).
					eq(TbAssignRecord::getLatest, 1).
					orderByDesc(TbAssignRecord::getCreateTime).
					last("limit 1"));
//			TbAssignRecord assignRecord = assignRecordMapper.selectAssignRecordByAssignId(clueId,
//					TbAssignRecord.RecordType.CLUES.getValue());
			if (assignRecord != null && assignRecord.getUserId().equals(userId)) {
				Date repeatGetTime = JobUtils.getDate(rulePool.getRepeatGetTime().intValue(), rulePool.getRepeatType(),
						assignRecord.getCreateTime());
				// 捞取限制时间内，不让捞取
				if (DateUtils.getNowDate().before(repeatGetTime)) {
					// 批量捞取跳过
					if (isBatch) {
						continue;
					} else {
						throw new CustomException("捞取失败！需要在 " + DateUtils.dateTimeHm(repeatGetTime) + " 后捞取");
					}
				}
			}
			// 捞取后下次跟进时间，及状态重置
			this.update(new LambdaUpdateWrapper<TbClue>().eq(TbClue::getId, clueId).
					set(TbClue::getStatus, TbClue.StatusType.UNFOLLOWED.getValue()).
					set(TbClue::getNextTime, null));
//			tbClueMapper.resetNextTimeAndStatus(clueId, TbClue.StatusType.UNFOLLOWED.getValue());
			// 新建分配记录
			TbAssignRecord tbAssignRecord = addNewRecord(clueId, userId);

			Date endDate = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
			this.update(new LambdaUpdateWrapper<TbClue>().eq(TbClue::getId, clueId).
					set(TbClue::getEndTime, endDate).
					set(TbClue::getNextTime, null));
//			tbClueMapper.updateClueEndTimeById(clueId, endDate);
		}
		return "全部捞取成功";
	}

	public TbAssignRecord addNewRecord(Long id, Long userId) {
		// 保留上一条分配记录
		// update tb_assign_record set latest='0' where assign_id = #{assignId} and type = #{type}
		assignRecordPlusMapper.update(null, new LambdaUpdateWrapper<TbAssignRecord>().
				set(TbAssignRecord::getLatest, 0).
				eq(TbAssignRecord::getType, TbAssignRecord.RecordType.CLUES.getValue()).
				eq(TbAssignRecord::getAssignId, id));
//		assignRecordMapper.updateLatest(id, TbAssignRecord.RecordType.CLUES.getValue());
		// 新建分配记录
		TbAssignRecord tbAssignRecord = new TbAssignRecord();
		tbAssignRecord.setAssignId(id);
		//连表查询用户信息
		SysUser sysUser = userMapper.selectUserById(userId);
		tbAssignRecord.setUserId(userId);
		tbAssignRecord.setDeptId(sysUser.getDeptId());
		tbAssignRecord.setUserName(sysUser.getUserName());
		Date now = DateUtils.getNowDate();
		tbAssignRecord.setCreateTime(now);
		tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
		tbAssignRecord.setType(TbAssignRecord.RecordType.CLUES.getValue());
		assignRecordPlusMapper.insert(tbAssignRecord);
		return tbAssignRecord;
	}

	@Override
	@Transactional
	public int falseClue(Long id, String reason, String remark) {
		TbClue tbClue = this.getOne(new LambdaQueryWrapper<TbClue>().eq(TbClue::getId, id));
//		TbClue tbClue = tbClueMapper.selectTbClueById(id);
		int falseCount = tbClue.getFalseCount();
		// 上报超过三次删除
		if (falseCount >= 2) {
			// 删除这条线索
			return this.remove(new LambdaQueryWrapper<TbClue>().eq(TbClue::getId, id))&&true?1:0;
//			return tbCluePMapper.removeClueByFalseClue(id);
//           return updateStatus(id, TbClue.StatusType.DELETED.getValue());
		}
		// 少于三次入线索池
		tbClue.setFalseCount(tbClue.getFalseCount() + 1);
		tbClue.setStatus(TbClue.StatusType.FALSE.getValue());
		updateTbClue(tbClue);
		updateStatus(tbClue.getId(), TbClue.StatusType.FALSE.getValue());
		// 伪线索原因
		TbClueTrackRecord trackRecord = new TbClueTrackRecord();
		trackRecord.setCreateBy(SecurityUtils.getUsername());
		trackRecord.setFalseReason(reason);
		trackRecord.setRecord(remark);
		trackRecord.setClueId(id);
		trackRecord.setType("1");
		trackRecord.setCreateTime(DateUtils.getNowDate());
		return tbClueTrackRecordPlusMapper.insert(trackRecord);
//		return tbClueTrackRecordMapper.insertTbClueTrackRecord(trackRecord);
	}

	@Override
	@Transactional
	public int updateStatus(Long clueId, String status) {
		//update tb_clue set next_time=null, status=#{status} where id = #{id}
		return this.update(new LambdaUpdateWrapper<TbClue>().eq(TbClue::getId, clueId).
				set(TbClue::getStatus, status).
				set(TbClue::getNextTime, null))&&true?1:0;
//		return tbClueMapper.resetNextTimeAndStatus(clueId, status);
	}

	/**
	 * 批量导入
	 */
	@Override
	public Map<String, Integer> addTbClue(List<TbClueExcelVo> cluevoList) {
		List<TbClue> clueList = cluevoList.stream().map(vo -> {
			TbClue tbClue = new TbClue();
			BeanUtils.copyProperties(vo, tbClue);
			tbClue.setCreateBy(SecurityUtils.getUsername());
			tbClue.setCreateTime(DateUtils.getNowDate());
			String activityCode = vo.getActivityCode();
			// 关联活动
			if (StringUtils.isNoneBlank(activityCode)) {
				TbActivity activity = activityService.selectTbActivityByCode(activityCode);
				if (activity != null) {
					tbClue.setActivityId(activity.getId());
				}
			}
			return tbClue;
		}).collect(Collectors.toList());
		return tbClueService.importClues(clueList);
	}

	/**
	 * 校验线索手机号是否存在
	 */
	@Override
	public boolean checkCluePhoneExis(String phone) {
		return this.list(new LambdaQueryWrapper<TbClue>().eq(TbClue::getPhone, phone)).size()>0?false:true;
	}
	
	/**
	 * 查询线索列表
	 * <where>
            <if test="id != null  and id != ''"> and clue.id like concat('%', #{id}, '%')</if>
            <if test="phone != null  and phone != ''"> and clue.phone like concat('%', #{phone}, '%')</if>
            <if test="channel != null  and channel != ''"> and clue.channel = #{channel}</if>
            <if test="activityId != null  and activityId != ''"> and clue.activity_id = #{activityId}</if>
            <if test="sex != null  and sex != ''"> and clue.sex = #{sex}</if>
            <if test="age != null "> and clue.age = #{age}</if>
            <if test="weixin != null  and weixin != ''"> and clue.weixin = #{weixin}</if>
            <if test="qq != null  and qq != ''"> and clue.qq = #{qq}</if>
            <if test="level != null  and level != ''"> and clue.level = #{level}</if>
            <if test="subject != null  and subject != ''"> and clue.subject = #{subject}</if>
            <if test="params.beginCreateTime != null and params.beginCreateTime != ''"><!-- 开始创建时间 -->
                and date_format(clue.create_time,'%y%m%d') &gt;= date_format(#{params.beginCreateTime},'%y%m%d')
            </if>
            <if test="params.endCreateTime != null and params.endCreateTime != ''"><!--  -->
                and date_format(clue.create_time,'%y%m%d') &lt;= date_format(#{params.endCreateTime},'%y%m%d')
            </if>
            <if test="nextTime != null "> and clue.next_time = #{nextTime}</if>
            <if test="owner != null  and owner != ''"> and r.user_name like concat('%', #{owner}, '%')</if>
            and r.latest = '1' and r.type='0' 

            <!-- 线索状态没有被查询，这里去掉状态转而添加if -->
			<if test="status != null and status !=''"> and clue.status = #{status}</if>
			<if test="status == null or status == ''"> and clue.status in ('1','2')</if>
            <!-- and clue.status in ('1','2') -->
            
        </where>
        <!-- 数据范围过滤 -->
        ${params.dataScope}
        order by clue.create_time desc
	 */
	@Override
	public Page<TbClue> selectTbClueList(TbClueDto tbClue) {
		LambdaQueryWrapper<TbClue> queryWrapper = new LambdaQueryWrapper<TbClue>();
		//id
		queryWrapper.like(
						tbClue.getId()!=null
						&&
						StringUtils.isNotEmpty(tbClue.getId()),
						TbClue::getId,
						tbClue.getId()
					);
		//phone
		queryWrapper.like(
				tbClue.getPhone()!=null
				&&
				StringUtils.isNotEmpty(tbClue.getPhone()),
				TbClue::getPhone,
				tbClue.getPhone()
			);
		//channel
		queryWrapper.eq(
				tbClue.getChannel()!=null
				&&
				StringUtils.isNotEmpty(tbClue.getChannel()),
				TbClue::getChannel,
				tbClue.getChannel()
			);
		//subject
		queryWrapper.eq(
				tbClue.getSubject()!=null
				&&
				StringUtils.isNotEmpty(tbClue.getSubject()),
				TbClue::getSubject,
				tbClue.getSubject()
			);
		//创建开始时间 创建结束时间
		queryWrapper.between(
					(
						tbClue.getBeginCreateTime()!=null
						&&
						StringUtils.isNotEmpty(tbClue.getBeginCreateTime())
					)
					&&
					(
						tbClue.getEndCreateTime()!=null
						&&
						StringUtils.isNotEmpty(tbClue.getEndCreateTime())
					),
					TbClue::getCreateTime,
					tbClue.getBeginCreateTime(), 
					tbClue.getEndCreateTime());
		//线索归属
		queryWrapper.eq(
					tbClue.getOwner()!=null
					&&
					StringUtils.isNotEmpty(tbClue.getOwner()),
				TbClue::getOwner,
				tbClue.getOwner()
				);
		//status
		if(tbClue.getStatus()!=null&&StringUtils.isNotEmpty(tbClue.getStatus())) {
			queryWrapper.eq(
					TbClue::getStatus,
					tbClue.getStatus()
				);
		}else{
			queryWrapper.in(TbClue::getStatus, new ArrayList<String>(){{
				add("1");
				add("2");
			}});
		}
		queryWrapper.orderByDesc(TbClue::getCreateTime);
		Page<TbClue> result = this.page(new Page(tbClue.getPageNum(),tbClue.getPageSize()),queryWrapper);
		
		LambdaQueryWrapper<TbAssignRecord> assignRecordQueryWrapper = new LambdaQueryWrapper<TbAssignRecord>();
		for (TbClue index : result.getRecords()) {
			assignRecordQueryWrapper.eq(TbAssignRecord::getAssignId, index.getId());
			TbAssignRecord record = assignRecordPlusMapper.selectOne(assignRecordQueryWrapper);
			if(record!=null) {
				index.setOwner(record.getUserName());
			}
		}
		return result;
	}
	
	/**
	 * 分页查询规则池
	 */
	@Override
	public Page<TbClue> selectTbCluePool(TbClueDto tbClue) {
		LambdaQueryWrapper<TbClue> lambdaQueryWrapper = new LambdaQueryWrapper<TbClue>();
		lambdaQueryWrapper.like(TbClue::getId, 
				tbClue.getId());
		lambdaQueryWrapper.like(TbClue::getPhone, 
				tbClue.getPhone());
		lambdaQueryWrapper.eq(TbClue::getChannel, 
				tbClue.getChannel());
		lambdaQueryWrapper.between(TbClue::getCreateTime, 
				tbClue.getBeginCreateTime(),
				tbClue.getEndCreateTime());
		return null;
	}

	@Override
	public int insertTbClue(TbClueDto clue) {
		TbClue tbClue = new TbClue();
		BeanUtils.copyProperties(clue, tbClue);
		return insertTbClue(tbClue);
	}
	
	
	/**
	 * 修改线索管理
	 *  update tb_clue
        <trim prefix="SET" suffixOverrides=",">
            <if test="name != null">name = #{name},</if>
            <if test="phone != null and phone != ''">phone = #{phone},</if>
            <if test="channel != null and channel != ''">channel = #{channel},</if>
            <if test="activityId != null">activity_id = #{activityId},</if>
            <if test="sex != null">sex = #{sex},</if>
            <if test="age != null">age = #{age},</if>
            <if test="weixin != null">weixin = #{weixin},</if>
            <if test="qq != null">qq = #{qq},</if>
            <if test="level != null">level = #{level},</if>
            <if test="subject != null">subject = #{subject},</if>
            <if test="status != null">status = #{status},</if>
            <if test="owner != null">owner = #{owner},</if>
            <if test="createTime != null">create_time = #{createTime},</if>
            <if test="createBy != null">create_by = #{createBy},</if>
            <if test="assignBy != null">assign_by = #{assignBy},</if>
            <if test="assignTime != null">assign_time = #{assignTime},</if>
            <if test="falseCount != null">false_count = #{falseCount},</if>
            <if test="nextTime != null">next_time = #{nextTime},</if>
        </trim>
        where id = #{id}
	 * @param tbClue 线索管理
	 * @return 结果
	 */
	@Override
	public int updateTbClue(TbClueDto tbClue) {
		LambdaUpdateWrapper<TbClue> updateWrapper = new LambdaUpdateWrapper<TbClue>();
		updateWrapper.eq(TbClue::getId, tbClue.getId());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getName()),TbClue::getName,tbClue.getName());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getPhone()),TbClue::getPhone,tbClue.getPhone());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getChannel()),TbClue::getChannel,tbClue.getChannel());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getActivityId()),TbClue::getActivityId,tbClue.getActivityId());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getSex()),TbClue::getSex,tbClue.getSex());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getAge()),TbClue::getAge,tbClue.getAge());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getWeixin()),TbClue::getWeixin,tbClue.getWeixin());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getQq()),TbClue::getQq,tbClue.getQq());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getLevel()),TbClue::getLevel,tbClue.getLevel());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getSubject()),TbClue::getSubject,tbClue.getSubject());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getStatus()),TbClue::getStatus,tbClue.getStatus());
		updateWrapper.set(StringUtils.isNotEmpty(tbClue.getOwner()),TbClue::getOwner,tbClue.getOwner());
		return this.update(updateWrapper)&&true ? 1 : 0;
	}
}
