package com.huike.contract.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.business.domain.TbBusiness;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.business.mapper.mybatis_plus.TbBusinessPlusMapper;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.clues.mapper.TbCourseMapper;
import com.huike.clues.mapper.mybatis_plus.TbActivityPlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbCoursePlusMapper;
import com.huike.clues.service.ISysDictDataService;
import com.huike.common.core.redis.RedisCache;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.bean.BeanUtils;
import com.huike.contract.domain.TbContract;
import com.huike.contract.dto.TbContractDto;
import com.huike.contract.mapper.TbContractMapper;
import com.huike.contract.mapper.mybatis_plus.TbContractPlusMapper;
import com.huike.contract.service.ITbContractService;

/**
 * 合同Service业务层处理
 * 
 */
@Service
public class TbContractServiceImpl extends ServiceImpl<TbContractPlusMapper, TbContract> implements ITbContractService {
	@Autowired
	private TbContractMapper tbContractMapper;

	@Autowired
	private TbBusinessMapper tbBusinessMapper;
	
	@Autowired
	private TbBusinessPlusMapper tbBusinessPlusMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private TbActivityMapper activityMapper;

	@Autowired
	private TbActivityPlusMapper activityPlusMapper;
	
	@Autowired
	private TbCourseMapper courseMapper;
	
	@Autowired
	private TbCoursePlusMapper coursePlusMapper;
	
	private ISysDictDataService sysDictDataService;

	@Override
	public TbContract selectTbContractById(Long id) {
		return this.getById(id);
	}

	/**
	 * 查询合同列表
	 */
	@Override
	public List<TbContract> selectTbContract(TbContract queryConditon) {
		String userName = SecurityUtils.getUsername();
		queryConditon.setCreateBy(userName);
		List<TbContract> list = tbContractMapper.selectTbContractList(queryConditon);
		return list;
	}

	/**
	 * 新增合同
	 * 
	 * @param tbContract 合同
	 * @return 结果
	 */
	@Override
	public int insertTbContract(TbContract tbContract) {
		String businessKey = "" + System.nanoTime();
		tbContract.setId(businessKey);
		tbContract.setCreateBy(SecurityUtils.getUsername());
		tbContract.setCreateTime(DateUtils.getNowDate());
		tbContract.setCreateBy(SecurityUtils.getUsername());
		tbContract.setCreateTime(DateUtils.getNowDate());
		tbContract.setDeptId(SecurityUtils.getDeptId());
		tbContract.setStatus("4");// 状态(待审核1，已通过2，已驳回3 全部完成4)
		setOrder(tbContract);
		return this.save(tbContract)&&true?1:0;
//		return tbContractMapper.insertTbContract(tbContract);
	}

	@Override
	public int changeContract(Long businessId, TbContract tbContract) {
		TbBusiness tbBusiness = tbBusinessMapper.selectTbBusinessById(businessId);
		if (tbBusiness.getCourseId() == null) {
			throw new CustomException("商机里面没有选择课程，无法转换客户合同");
		}

		tbBusiness.setStatus(TbBusiness.StatusType.TOCUSTOMER.getValue());
		/**
		 * update tb_business
        <trim prefix="SET" suffixOverrides=",">
            <if test="name != null">name = #{name},</if>
            <if test="phone != null">phone = #{phone},</if>
            <if test="channel != null">channel = #{channel},</if>
            <if test="activityId != null">activity_id = #{activityId},</if>
            <if test="provinces != null">provinces = #{provinces},</if>
            <if test="city != null">city = #{city},</if>
            <if test="sex != null and sex != ''">sex = #{sex},</if>
            <if test="age != null">age = #{age},</if>
            <if test="weixin != null">weixin = #{weixin},</if>
            <if test="qq != null">qq = #{qq},</if>
            <if test="level != null">level = #{level},</if>
            <if test="subject != null">subject = #{subject},</if>
            <if test="courseId != null">course_id = #{courseId},</if>
            <if test="createBy != null">create_by = #{createBy},</if>
            <if test="createTime != null">create_time = #{createTime},</if>
            <if test="occupation != null">occupation = #{occupation},</if>
            <if test="education != null">education = #{education},</if>
            <if test="job != null">job = #{job},</if>
            <if test="salary != null">salary = #{salary},</if>
            <if test="major != null">major = #{major},</if>
            <if test="expectedSalary != null">expected_salary = #{expectedSalary},</if>
            <if test="reasons != null">reasons = #{reasons},</if>
            <if test="plan != null">plan = #{plan},</if>
            <if test="planTime != null">plan_time = #{planTime},</if>
            <if test="otherIntention != null">other_intention = #{otherIntention},</if>
            <if test="nextTime != null">next_time = #{nextTime},</if>
            <if test="status != null">status = #{status},</if>
        </trim>
        where id = #{id}
		 */
		tbBusinessPlusMapper.update(tbBusiness,new LambdaUpdateWrapper<TbBusiness>().
				set(StringUtils.isNotEmpty(tbBusiness.getName()),TbBusiness::getName,tbBusiness.getName()).
				set(StringUtils.isNotEmpty(tbBusiness.getPhone()),TbBusiness::getPhone, tbBusiness.getPhone()).
				set(StringUtils.isNotEmpty(tbBusiness.getChannel()),TbBusiness::getChannel, tbBusiness.getChannel()).
				set(ObjectUtils.isNotEmpty(tbBusiness.getActivityId()),TbBusiness::getActivityId, tbBusiness.getActivityId()).
				set(StringUtils.isNotEmpty(tbBusiness.getProvinces()),TbBusiness::getProvinces, tbBusiness.getProvinces()).
				set(StringUtils.isNotEmpty(tbBusiness.getCity()),TbBusiness::getCity, tbBusiness.getCity()).
				set(StringUtils.isNotEmpty(tbBusiness.getSex()),TbBusiness::getSex, tbBusiness.getSex()).
				set(ObjectUtils.isNotEmpty(tbBusiness.getAge()),TbBusiness::getAge, tbBusiness.getAge()).
				set(StringUtils.isNotEmpty(tbBusiness.getWeixin()),TbBusiness::getWeixin, tbBusiness.getWeixin()).
				set(StringUtils.isNotEmpty(tbBusiness.getQq()),TbBusiness::getQq, tbBusiness.getQq()).
				set(StringUtils.isNotEmpty(tbBusiness.getLevel()),TbBusiness::getLevel, tbBusiness.getLevel()).
				set(StringUtils.isNotEmpty(tbBusiness.getSubject()),TbBusiness::getSubject, tbBusiness.getSubject()).
				set(ObjectUtils.isNotEmpty(tbBusiness.getCourseId()),TbBusiness::getCourseId, tbBusiness.getCourseId()).
				set(StringUtils.isNotEmpty(tbBusiness.getCreateBy()),TbBusiness::getCreateBy, tbBusiness.getCreateBy()).
				set(ObjectUtils.isNotEmpty(tbBusiness.getCreateTime()),TbBusiness::getCreateTime, tbBusiness.getCreateTime()).
				set(StringUtils.isNotEmpty(tbBusiness.getOccupation()),TbBusiness::getOccupation, tbBusiness.getOccupation()).
				set(StringUtils.isNotEmpty(tbBusiness.getEducation()),TbBusiness::getEducation, tbBusiness.getEducation()).
				set(StringUtils.isNotEmpty(tbBusiness.getSalary()),TbBusiness::getSalary, tbBusiness.getSalary()).
				set(StringUtils.isNotEmpty(tbBusiness.getMajor()),TbBusiness::getMajor, tbBusiness.getMajor()).
				set(StringUtils.isNotEmpty(tbBusiness.getExpectedSalary()),TbBusiness::getExpectedSalary, tbBusiness.getExpectedSalary()).
				set(StringUtils.isNotEmpty(tbBusiness.getReasons()),TbBusiness::getReasons, tbBusiness.getReasons()).
				set(StringUtils.isNotEmpty(tbBusiness.getPlan()),TbBusiness::getPlan, tbBusiness.getPlan()).
				set(ObjectUtils.isNotEmpty(tbBusiness.getPlanTime()),TbBusiness::getPlanTime, tbBusiness.getPlanTime()).
				set(ObjectUtils.isNotEmpty(tbBusiness.getNextTime()),TbBusiness::getNextTime, tbBusiness.getNextTime()).
				set(StringUtils.isNotEmpty(tbBusiness.getOtherIntention()),TbBusiness::getOtherIntention, tbBusiness.getOtherIntention()).
				set(StringUtils.isNotEmpty(tbBusiness.getStatus()),TbBusiness::getStatus, tbBusiness.getStatus())
				);
//		tbBusinessMapper.updateTbBusiness(tbBusiness);
		tbContract.setCreateTime(DateUtils.getNowDate());
		tbContract.setBusinessId(businessId);
		BeanUtils.copyProperties(tbBusiness, tbContract);
		return insertTbContract(tbContract);
		// return tbContractMapper.insertTbContract(tbContract);
	}

	/**
	 * 修改合同
	 * 
	 * @param tbContract 合同
	 * @return 结果
	 * update tb_contract
        <trim prefix="SET" suffixOverrides=",">
            <if test="contractNo != null">contract_no = #{contractNo},</if>
            <if test="phone != null">phone = #{phone},</if>
            <if test="name != null and name != ''">name = #{name},</if>
            <if test="subject != null">subject = #{subject},</if>
            <if test="channel != null">channel = #{channel},</if>
            <if test="courseId != null">course_id = #{courseId},</if>
            <if test="activityId != null">activity_id = #{activityId},</if>
            <if test="status != null">status = #{status},</if>
            <if test="createBy != null">create_by = #{createBy},</if>
            <if test="createTime != null">create_time = #{createTime},</if>
            <if test="order != null">contract_order = #{order},</if>
            <if test="fileName != null">file_name = #{fileName},</if>
            <if test="discountType != null">discount_type = #{discountType},</if>
            <if test="coursePrice != null">course_price = #{coursePrice},</if>
        </trim>
        where id = #{id}
	 */
	@Override
	public int updateTbContract(TbContract tbContract) {
		tbContract.setStatus("2");
		setOrder(tbContract);
		return this.update(new LambdaUpdateWrapper<TbContract>().
				set(StringUtils.isNotEmpty(tbContract.getContractNo()),TbContract::getContractNo, tbContract.getContractNo()).
				set(StringUtils.isNotEmpty(tbContract.getPhone()),TbContract::getPhone, tbContract.getPhone()).
				set(StringUtils.isNotEmpty(tbContract.getName()),TbContract::getName, tbContract.getName()).
				set(StringUtils.isNotEmpty(tbContract.getSubject()),TbContract::getSubject, tbContract.getSubject()).
				set(StringUtils.isNotEmpty(tbContract.getChannel()),TbContract::getChannel, tbContract.getChannel()).
				set(StringUtils.isNotEmpty(tbContract.getCourseId()+""),TbContract::getCourseId, tbContract.getCourseId()).
				set(StringUtils.isNotEmpty(tbContract.getActivityId()+""),TbContract::getActivityId, tbContract.getActivityId()).
				set(StringUtils.isNotEmpty(tbContract.getStatus()),TbContract::getStatus, tbContract.getStatus()).
				set(StringUtils.isNotEmpty(tbContract.getCreateBy()),TbContract::getCreateBy, tbContract.getCreateBy()).
				set(StringUtils.isNotEmpty(tbContract.getCreateTime()+""),TbContract::getCreateTime, tbContract.getCreateTime()).
				set(StringUtils.isNotEmpty(tbContract.getOrder()+""),TbContract::getOrder, tbContract.getOrder()).
				set(StringUtils.isNotEmpty(tbContract.getFileName()),TbContract::getFileName, tbContract.getFileName()).
				set(StringUtils.isNotEmpty(tbContract.getCoursePrice()+""),TbContract::getCoursePrice, tbContract.getCoursePrice()).
				set(StringUtils.isNotEmpty(tbContract.getDiscountType()),TbContract::getDiscountType, tbContract.getDiscountType()))
				&&true?1:0;
	}

	private void setOrder(TbContract tbContract) {
		TbCourse course = coursePlusMapper.selectById(tbContract.getId());
//		TbCourse course = courseMapper.selectById(tbContract.getId());
		tbContract.setCoursePrice(course.getPrice());
		TbActivity tbActivity = activityPlusMapper.selectById(tbContract.getActivityId());
//		TbActivity tbActivity = activityMapper.selectTbActivityById(tbContract.getActivityId());
		if (tbActivity != null) {
			if ("1".equals(tbActivity.getType())) {
				tbContract.setDiscountType("课程折扣");
				BigDecimal price = new BigDecimal(course.getPrice());
				BigDecimal disCount = new BigDecimal(tbActivity.getDiscount());
				BigDecimal order = price.multiply(disCount.divide(new BigDecimal(10), 2, BigDecimal.ROUND_CEILING));
				tbContract.setOrder(order.floatValue());
//                tbContract.setOrder(course.getPrice()*tbActivity.getDiscount());
			} else {
				// 当代金券大于等于合同50% 按照原价
				if (tbActivity.getVouchers() >= course.getPrice() / 2) {
					tbContract.setOrder(course.getPrice());
					tbContract.setDiscountType("代金券大于原课程50%,代金券不可用");
				} else {
					tbContract.setDiscountType("代金券");
					tbContract.setOrder(course.getPrice() - tbActivity.getVouchers());
				}
			}
		} else {
			tbContract.setDiscountType("无");
			tbContract.setOrder(course.getPrice());
		}
	}

	/**
	 * 分页查询所有的合同
	 */
	@Override
	public Page<TbContract> selectTbContract(TbContractDto contractDto) {
		String userName = SecurityUtils.getUsername();
		LambdaQueryWrapper<TbContract> queryWrapper = new LambdaQueryWrapper<TbContract>();
		// contractNo
		queryWrapper.like(contractDto.getContractNo() != null && StringUtils.isNotEmpty(contractDto.getContractNo()),
				TbContract::getContractNo, contractDto.getContractNo());
		// channel
		queryWrapper.eq(contractDto.getChannel() != null && StringUtils.isNotEmpty(contractDto.getChannel()),
				TbContract::getChannel, contractDto.getChannel());
		// phone
		queryWrapper.eq(contractDto.getPhone() != null && StringUtils.isNotEmpty(contractDto.getPhone()),
				TbContract::getPhone, contractDto.getPhone());
		// name
		queryWrapper.like(contractDto.getName() != null && StringUtils.isNotEmpty(contractDto.getName()),
				TbContract::getName, contractDto.getName());
		// subject
		queryWrapper.eq(contractDto.getSubject() != null && StringUtils.isNotEmpty(contractDto.getSubject()),
				TbContract::getSubject, contractDto.getSubject());
		// courseId
		queryWrapper.eq(contractDto.getCourseId() != null && StringUtils.isNotEmpty(contractDto.getCourseId()),
				TbContract::getCourseId, contractDto.getCourseId());
		// createBy
		queryWrapper.eq(userName != null && StringUtils.isNotEmpty(userName), TbContract::getCreateBy, userName);
		// beginCreateTime endCreateTime
		queryWrapper.between(
				(contractDto.getBeginCreateTime() != null && StringUtils.isNotEmpty(contractDto.getBeginCreateTime()))
						&& (contractDto.getEndCreateTime() != null
								&& StringUtils.isNotEmpty(contractDto.getEndCreateTime())),
				TbContract::getCreateTime, contractDto.getBeginCreateTime(), contractDto.getEndCreateTime());
		Page<TbContract> result = this.page(new Page<TbContract>(contractDto.getPageNum(), contractDto.getPageSize()),
				queryWrapper);
		return result;
	}

}