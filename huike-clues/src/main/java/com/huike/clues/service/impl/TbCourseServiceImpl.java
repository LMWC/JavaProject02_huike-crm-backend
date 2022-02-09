package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.dto.TbCourseDto;
import com.huike.clues.mapper.TbCourseMapper;
import com.huike.clues.service.ITbCourseService;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 课程管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-04-01
 */
@Service
public class TbCourseServiceImpl extends ServiceImpl<TbCourseMapper, TbCourse> implements ITbCourseService {

	/**
	 * 查询课程管理列表
	 * 
	 * @return 课程管理
	 */
	@Override
	public TableDataInfo list(TbCourseDto dto) {

		LambdaQueryWrapper<TbCourse> lambdaQueryWrapper = new LambdaQueryWrapper();
		if (StringUtils.isNotBlank(dto.getCode())) {
			lambdaQueryWrapper.eq(TbCourse::getCode, dto.getCode());
		}
		if (StringUtils.isNotBlank(dto.getName())) {
			lambdaQueryWrapper.like(TbCourse::getName, dto.getName());
		}
		if (StringUtils.isNotBlank(dto.getApplicablePerson())) {
			lambdaQueryWrapper.eq(TbCourse::getApplicablePerson, dto.getApplicablePerson());
		}
		if (StringUtils.isNotBlank(dto.getSubject())) {
			lambdaQueryWrapper.eq(TbCourse::getSubject, dto.getSubject());
		}
		if (StringUtils.isNotBlank(dto.getBeginCreateTime())) {
			lambdaQueryWrapper.ge(TbCourse::getCreateTime, dto.getBeginCreateTime());
		}
		if ((StringUtils.isNotBlank(dto.getEndCreateTime()))) {
			lambdaQueryWrapper.le(TbCourse::getCreateTime, dto.getEndCreateTime());
		}
		Integer pageNum = dto.getPageNum();
		Integer pageSize = dto.getPageSize();
		IPage<TbCourse> page = new Page<TbCourse>(pageNum, pageSize);
		this.page(page, lambdaQueryWrapper);

		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(HttpStatus.SUCCESS);
		rspData.setMsg("查询成功");
		rspData.setRows(page.getRecords());
		rspData.setTotal(page.getTotal());
		return rspData;
	}

	@Override
	public boolean add(TbCourse tbCourse) {
		tbCourse.setCreateTime(new Date());
		tbCourse.setCode(StringUtils.getRandom(8));
		return this.save(tbCourse);
	}
}
