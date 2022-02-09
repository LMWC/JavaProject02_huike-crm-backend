package com.huike.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.business.domain.TbBusiness;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.TbClue;
import com.huike.clues.mapper.mybatis_plus.TbCluePlusMapper;
import com.huike.clues.service.ISysDictDataService;
import com.huike.common.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huike.business.mapper.TbBusinessTrackRecordMapper;
import com.huike.business.mapper.mybatis_plus.TbBusinessTrackRecordPlusMapper;
import com.huike.business.domain.TbBusinessTrackRecord;
import com.huike.business.dto.TbBusinessTrackRecordDto;
import com.huike.business.service.ITbBusinessTrackRecordService;

/**
 * 商机跟进记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-04-28
 */
@Service
public class TbBusinessTrackRecordServiceImpl extends ServiceImpl<TbBusinessTrackRecordPlusMapper,TbBusinessTrackRecord>  implements ITbBusinessTrackRecordService {
	@Autowired
	private TbBusinessTrackRecordMapper tbBusinessTrackRecordMapper;

	@Autowired
	private TbBusinessMapper tbBusinessMapper;

	/**
	 * 查询商机跟进记录
	 * 
	 * @param id 商机跟进记录ID
	 * @return 商机跟进记录
	 */
	@Override
	public TbBusinessTrackRecord selectTbBusinessTrackRecordById(Long id) {
		LambdaQueryWrapper<TbBusinessTrackRecord> queryWrapper = new LambdaQueryWrapper<TbBusinessTrackRecord>();
		queryWrapper.eq(TbBusinessTrackRecord::getId, id);
		return this.getOne(queryWrapper);
//		return tbBusinessTrackRecordMapper.selectTbBusinessTrackRecordById(id);
	}

	/**
	 * 查询商机跟进记录列表
	 * 
	 * @param tbBusinessTrackRecord 商机跟进记录
	 * @return 商机跟进记录
	 */
	@Override
	public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordList(TbBusinessTrackRecord tbBusinessTrackRecord) {
		return tbBusinessTrackRecordMapper.selectTbBusinessTrackRecordList(tbBusinessTrackRecord);
	}

	/**
	 * 新增商机跟进记录
	 * 
	 * @param tbBusinessTrackRecord 商机跟进记录
	 * @return 结果
	 */
	@Override
	public int insertTbBusinessTrackRecord(TbBusinessTrackRecord tbBusinessTrackRecord) {
		tbBusinessTrackRecord.setCreateTime(DateUtils.getNowDate());
		return tbBusinessTrackRecordMapper.insertTbBusinessTrackRecord(tbBusinessTrackRecord);
	}

	@Override
	public int insertTbBusinessTrackRecord(TbBusiness tbBusiness, TbBusinessTrackRecord tbBusinessTrackRecord) {
		tbBusinessMapper.updateTbBusiness(tbBusiness);
		return this.save(tbBusinessTrackRecord)&&true?1:0;
//		return tbBusinessTrackRecordMapper.insertTbBusinessTrackRecord(tbBusinessTrackRecord);
	}

	/**
	 * 商机跟进记录
	 */
	@Override
	public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordList(TbBusinessTrackRecordDto dto) {
		LambdaQueryWrapper<TbBusinessTrackRecord> queryWrapper = new LambdaQueryWrapper<TbBusinessTrackRecord>();
		queryWrapper.eq(StringUtils.isNotEmpty(dto.getBusinessId()+""), TbBusinessTrackRecord::getBusinessId,dto.getBusinessId());
		queryWrapper.eq(StringUtils.isNotEmpty(dto.getKeyItems()), TbBusinessTrackRecord::getKeyItems,dto.getKeyItems());
		queryWrapper.eq(StringUtils.isNotEmpty(dto.getRecord()), TbBusinessTrackRecord::getRecord,dto.getRecord());
		queryWrapper.eq(StringUtils.isNotEmpty(dto.getTrackStatus()), TbBusinessTrackRecord::getTrackStatus,dto.getTrackStatus());
		return this.list(queryWrapper);
	}
}
