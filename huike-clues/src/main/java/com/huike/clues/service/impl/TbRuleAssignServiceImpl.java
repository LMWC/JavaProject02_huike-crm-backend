package com.huike.clues.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbRuleAssign;
import com.huike.clues.domain.TbRuleExpression;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.dto.TbRuleAssignDto;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.mapper.TbRuleAssignMapper;
import com.huike.clues.mapper.TbRuleExpressionMapper;
import com.huike.clues.mapper.mybatis_plus.SysUserPlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbAssignRecordPlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbCluePlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbRuleAssignPlusMapper;
import com.huike.clues.service.ITbClueService;
import com.huike.clues.service.ITbRuleAssignService;
import com.huike.clues.service.ITbRulePoolService;
import com.huike.clues.utils.HuiKeCrmDateUtils;
import com.huike.clues.utils.RuleUtils;
import com.huike.common.constant.Constants;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;

/**
 * 规则分配Service业务层处理
 * 
 * @date 2021-04-08
 */
@Service
public class TbRuleAssignServiceImpl extends ServiceImpl<TbRuleAssignPlusMapper, TbRuleAssign> implements ITbRuleAssignService {
	@Autowired
	private TbRuleAssignMapper tbRuleAssignMapper;

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysUserPlusMapper sysUserPlusMapper;

	@Autowired
	TbRuleExpressionMapper tbRuleExpressionMapper;

	@Autowired
	ITbRulePoolService rulePoolService;

	@Autowired
	private TbAssignRecordMapper assignRecordMapper;
	
	@Autowired
	private TbAssignRecordPlusMapper assignRecordPlusMapper;

	@Autowired
	private SysUserMapper userMapper;

	@Autowired
	private ITbClueService tbclueService;

	@Autowired
	private TbClueMapper tbClueMapper;
	
	@Autowired
	private TbCluePlusMapper tbCluePlusMapper;

	
	
	/**
	 * 查询规则分配
	 * select id, rule_condition, rule_expression, assign_object, assign_type, rule_order, create_by, create_time,type from tb_rule_assign
	 * where id = #{id}
	 * @param id 规则分配ID
	 * @return 规则分配
	 */
	@Override
	public TbRuleAssign selectTbRuleAssignById(Long id) {
		return this.getOne(new LambdaQueryWrapper<TbRuleAssign>().eq(TbRuleAssign::getId, id));
//		return tbRuleAssignMapper.selectTbRuleAssignById(id);
	}

	/**
	 * 查询规则分配列表
<if test="ruleCondition != null  and ruleCondition != ''"> and rule_condition = #{ruleCondition}</if>
<if test="ruleExpression != null  and ruleExpression != ''"> and rule_expression = #{ruleExpression}</if>
<if test="assignObject != null  and assignObject != ''"> and assign_object = #{assignObject}</if>
<if test="assignType != null  and assignType != ''"> and assign_type = #{assignType}</if>
<if test="ruleOrder != null "> and rule_order = #{ruleOrder}</if>
<if test="type != null "> and type = #{type}</if>
	 * @param tbRuleAssign 规则分配
	 * @return 规则分配
	 */
	@Override
	public List<TbRuleAssign> selectTbRuleAssignList(TbRuleAssign tbRuleAssign) {
		return this.list(new LambdaQueryWrapper<TbRuleAssign>().
				eq(StringUtils.isNotEmpty(tbRuleAssign.getRuleCondition()),TbRuleAssign::getRuleCondition, tbRuleAssign.getRuleCondition()).
				eq(StringUtils.isNotEmpty(tbRuleAssign.getRuleExpression()),TbRuleAssign::getRuleExpression, tbRuleAssign.getRuleExpression()).
				eq(StringUtils.isNotEmpty(tbRuleAssign.getAssignObject()),TbRuleAssign::getAssignObject, tbRuleAssign.getAssignObject()).
				eq(StringUtils.isNotEmpty(tbRuleAssign.getAssignType()),TbRuleAssign::getAssignType, tbRuleAssign.getAssignType()).
				eq(ObjectUtils.isNotEmpty(tbRuleAssign.getRuleOrder()),TbRuleAssign::getRuleOrder, tbRuleAssign.getRuleOrder()).
				eq(StringUtils.isNotEmpty(tbRuleAssign.getType()),TbRuleAssign::getType, tbRuleAssign.getType())
				);
//		return tbRuleAssignMapper.selectTbRuleAssignList(tbRuleAssign);
	}

	/**
	 * 新增规则分配
	 * 
	 * @param tbRuleAssign 规则分配
	 * @return 结果
	 */
	@Override
	@Transactional
	public boolean insertTbRuleAssign(TbRuleAssign tbRuleAssign) {
		tbRuleAssign.setCreateBy(SecurityUtils.getUsername());
		tbRuleAssign.setCreateTime(DateUtils.getNowDate());
		tbRuleAssign.setRuleOrder(1L);
		tbRuleAssign.setRuleExpression(RuleUtils.getExpression(tbRuleAssign));
		List<TbRuleExpression> rulesList = tbRuleAssign.getRulesList();
		this.save(tbRuleAssign);
//		tbRuleAssignMapper.insertTbRuleAssign(tbRuleAssign);
		Long ruleId = tbRuleAssign.getId();
		rulesList.forEach(e -> {
			e.setRuleId(ruleId);
		});
		tbRuleExpressionMapper.insertBatch(rulesList);
		loadRule(tbRuleAssign.getType());
		return true;
	}

	/**
	 * 修改规则分配
	 * 
	 * @param tbRuleAssign 规则分配
	 * @return 结果
	 */
	@Override
	@Transactional
	public boolean updateTbRuleAssign(TbRuleAssign tbRuleAssign) {
		// 删除关联关系
		tbRuleExpressionMapper.deleteTbRuleExpressionByRuleIds(new Long[] { tbRuleAssign.getId() });
		List<TbRuleExpression> rulesList = tbRuleAssign.getRulesList();
		Long ruleId = tbRuleAssign.getId();
		rulesList.forEach(e -> {
			e.setRuleId(ruleId);
		});
		tbRuleExpressionMapper.insertBatch(rulesList);
		tbRuleAssign.setRuleExpression(RuleUtils.getExpression(tbRuleAssign));
		tbRuleAssignMapper.updateTbRuleAssign(tbRuleAssign);
		loadRule(tbRuleAssign.getType());
		return true;
	}

	@Override
	public void updateOrders(List<TbRuleAssign> list) {
		String type = "";
		for (TbRuleAssign tbRuleAssign : list) {
			type = tbRuleAssign.getType();
			tbRuleAssignMapper.updateTbRuleAssign(tbRuleAssign);
		}
		loadRule(type);
	}

	/**
	 * 删除规则分配信息
	 * 
	 * @param id 规则分配ID
	 * @return 结果
	 */
	@Override
	public boolean deleteTbRuleAssignById(Long id) {
		TbRuleAssign ruleAssign = selectTbRuleAssignById(id);
		tbRuleAssignMapper.deleteTbRuleAssignById(id);
		tbRuleExpressionMapper.deleteTbRuleExpressionByRuleIds(new Long[] { id });
		loadRule(ruleAssign.getType());
		return true;
	}

	@Override
	public void loadRule() {
		loadRule(Constants.rule_type_clue);
		loadRule(Constants.rule_type_business);
	}

	private void loadRule(String type) {
		TbRuleAssign tbRuleAssign = new TbRuleAssign();
		tbRuleAssign.setType(type);
		List<TbRuleAssign> ruleAssigns = tbRuleAssignMapper.selectTbRuleAssignList(tbRuleAssign);
		if (!ruleAssigns.isEmpty()) {
			RuleUtils.buildRules(type, ruleAssigns);
		}
	}

	@Override
	public boolean assignment(Long id, String type, String assignUser, String assignType) {
		if (TbRuleAssign.AssignType.USER.getValue().equals(assignType)) {
			String[] users = assignUser.split(",");
			List<Long> list = new ArrayList<>();
			for (String str : users) {
				try {
					list.add(Long.parseLong(str));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			return assignmentUser(id, list, type);
		} else if (TbRuleAssign.AssignType.POST.getValue().equals(assignType)) {
			// 获取岗位下所有用户，分再配
			String[] postIds = assignUser.split(",");
			List usersList = sysUserMapper.selectUserListByPostIds(StringArray2LongArray(postIds));
			return assignmentUser(id, usersList, type);
		} else if (TbRuleAssign.AssignType.DEPARTMENT.getValue().equals(assignType)) {
			// 获取部门下所有用户，再分配
			String[] departmentIds = assignUser.split(",");
			List usersList = sysUserMapper.selectUserListByDeptIds(StringArray2LongArray(departmentIds));
			return assignmentUser(id, usersList, type);
		} else if (TbRuleAssign.AssignType.ROLE.getValue().equals(assignType)) {
			String[] roleIds = assignUser.split(",");
			List usersList = sysUserMapper.selectUserListByRoleIds(StringArray2LongArray(roleIds));
			return assignmentUser(id, usersList, type);
		}
		return true;
	}

	private boolean assignmentUser(Long id, List<Long> userIds, String type) {
		TbRulePool rulePool = rulePoolService.selectTbRulePoolByType(type);
		for (Long userId : userIds) {
			TbAssignRecord tbAssignRecord = new TbAssignRecord();
			// 统计当前分配人数目
			tbAssignRecord.setUserId(userId);
			tbAssignRecord.setLatest("1");
			tbAssignRecord.setType(type);
			/**
			 * <include refid="selectTbAssignRecordVo"/>
		        <where>  
		            <if test="assignId != null "> and assign_id = #{assignId}</if>
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
		        </where>
			 */
			List<TbAssignRecord> userAssignRecords = assignRecordPlusMapper.selectList(new LambdaQueryWrapper<TbAssignRecord>().
					eq(StringUtils.isNotEmpty(tbAssignRecord.getAssignId()+""),TbAssignRecord::getAssignId, tbAssignRecord.getAssignId()).
					eq(StringUtils.isNotEmpty(tbAssignRecord.getUserId()+""),TbAssignRecord::getUserId, tbAssignRecord.getUserId()).
					like(StringUtils.isNotEmpty(tbAssignRecord.getUserName()+""),TbAssignRecord::getUserName, tbAssignRecord.getUserName()).
					eq(StringUtils.isNotEmpty(tbAssignRecord.getDeptId()+""),TbAssignRecord::getDeptId, tbAssignRecord.getDeptId()).
					eq(StringUtils.isNotEmpty(tbAssignRecord.getLatest()),TbAssignRecord::getLatest, tbAssignRecord.getLatest()).
					eq(StringUtils.isNotEmpty(tbAssignRecord.getType()+""),TbAssignRecord::getType, tbAssignRecord.getType()).
					between(StringUtils.isNotEmpty(tbAssignRecord.getParams().get("endCreateTime")+"")
							&&
							StringUtils.isNotEmpty(tbAssignRecord.getParams().get("beginCreateTime")+""), 
							TbAssignRecord::getCreateTime, 
							tbAssignRecord.getParams().get("beginCreateTime")+"",
							tbAssignRecord.getParams().get("endCreateTime")+""));
//			List<TbAssignRecord> userAssignRecords = assignRecordMapper.selectAssignRecordList(tbAssignRecord);
			// 超过最大保有量 跳过当前用户
			if (userAssignRecords.size() >= rulePool.getMaxNunmber()) {
				continue;
			}

			SysUser sysUser = userMapper.selectUserById(userId);
			/**
			 * update tb_assign_record set latest='0' where assign_id = #{assignId} and type = #{type}
			 */
//			assignRecordMapper.updateLatest(id, type);
			assignRecordPlusMapper.update(tbAssignRecord, new LambdaUpdateWrapper<TbAssignRecord>().
					set(TbAssignRecord::getLatest, 0).
					eq(TbAssignRecord::getAssignId, id).
					eq(TbAssignRecord::getType, type));
			Date now = DateUtils.getNowDate();
			tbAssignRecord.setAssignId(id);
			tbAssignRecord.setDeptId(sysUser.getDeptId());
			tbAssignRecord.setUserName(sysUser.getUserName());
			tbAssignRecord.setCreateTime(now);
			tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
			tbAssignRecord.setType(type);
			assignRecordPlusMapper.insert(tbAssignRecord);
			// 添加回收，提醒规则
			Date endDate = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
			//update tb_clue set end_time=#{endTime},next_time = null where id = #{id}
//			tbClueMapper.updateClueEndTimeById(id, endDate);
			tbCluePlusMapper.update(null, new LambdaUpdateWrapper<TbClue>().
					eq(TbClue::getId, id).
					set(TbClue::getEndTime, endDate).
					set(TbClue::getNextTime, null));
			return true;
		}
		return false;
	}

	private static Long[] StringArray2LongArray(String[] stringArray) {
		List<Long> list = new ArrayList<>();
		for (String str : stringArray) {
			try {
				list.add(Long.parseLong(str));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		Long[] longArray = list.toArray(new Long[list.size()]);
		return longArray;
	}
	
	/**
	 *  <include refid="selectTbRuleAssignVo"/>
        <where>
            <if test="ruleCondition != null  and ruleCondition != ''"> and rule_condition = #{ruleCondition}</if>
            <if test="ruleExpression != null  and ruleExpression != ''"> and rule_expression = #{ruleExpression}</if>
            <if test="assignObject != null  and assignObject != ''"> and assign_object = #{assignObject}</if>
            <if test="assignType != null  and assignType != ''"> and assign_type = #{assignType}</if>
            <if test="ruleOrder != null "> and rule_order = #{ruleOrder}</if>
            <if test="type != null "> and type = #{type}</if>
        </where>
        order by rule_order,create_time
	 */
	@Override
	public Page<TbRuleAssign> selectTbRuleAssignPage(TbRuleAssignDto dto) {
		return this.page(new Page(dto.getPageNum(),dto.getPageSize()),new LambdaQueryWrapper<TbRuleAssign>().
				eq(StringUtils.isNotEmpty(dto.getRuleCondition()),TbRuleAssign::getRuleCondition, dto.getRuleCondition()).
				eq(StringUtils.isNotEmpty(dto.getRuleExpression()),TbRuleAssign::getRuleExpression,dto.getRuleExpression()).
				eq(StringUtils.isNotEmpty(dto.getAssignObject()),TbRuleAssign::getAssignObject,dto.getAssignObject()).
				eq(StringUtils.isNotEmpty(dto.getAssignType()),TbRuleAssign::getAssignType,dto.getAssignType()).
				eq(StringUtils.isNotEmpty(dto.getRuleOrder()+""),TbRuleAssign::getRuleOrder,dto.getRuleOrder()).
				eq(StringUtils.isNotEmpty(dto.getType()),TbRuleAssign::getType,dto.getType()).
				orderByAsc(TbRuleAssign::getRuleOrder,TbRuleAssign::getCreateTime));
//		return tbRuleAssignMapper.selectTbRuleAssignList(tbRuleAssign);
	}

}
