package com.huike.clues.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.dto.TbClueTrackRecordDto;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.mapper.TbClueTrackRecordMapper;
import com.huike.clues.mapper.mybatis_plus.TbCluePlusMapper;
import com.huike.clues.mapper.mybatis_plus.TbClueTrackRecordPlusMapper;
import com.huike.clues.service.ITbClueTrackRecordService;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;

/**
 * 线索跟进记录Service业务层处理
 * 
 * @date 2021-04-22
 */
@Service
public class TbClueTrackRecordServiceImpl extends ServiceImpl<TbClueTrackRecordPlusMapper,TbClueTrackRecord> implements ITbClueTrackRecordService {
	@Autowired
	private TbClueTrackRecordMapper tbClueTrackRecordMapper;

	@Autowired
	private TbClueMapper tbClueMapper;
	
	@Autowired
	private TbCluePlusMapper tbCluePlusMapper;

	/**
	 * 查询线索跟进记录
	 * 
	 * @param id 线索跟进记录ID
	 * @return 线索跟进记录
	 */
	@Override
	public TbClueTrackRecord selectTbClueTrackRecordById(Long id) {
//		return tbClueTrackRecordMapper.selectTbClueTrackRecordById(id);
		return this.getOne(new LambdaQueryWrapper<TbClueTrackRecord>().eq(TbClueTrackRecord::getId, id));
	}

	/**
	 * 查询线索跟进记录列表
	 * 
	 * @param tbClueTrackRecord 线索跟进记录
	 * @return 线索跟进记录
	 * select id, clue_id, create_by, subject, record, level, create_time, type, false_reason, next_time from tb_clue_track_record
	 * <where>  
            <if test="clueId != null  and clueId != ''"> and clue_id = #{clueId}</if>
            <if test="subject != null  and subject != ''"> and subject = #{subject}</if>
            <if test="record != null  and record != ''"> and record = #{record}</if>
            <if test="level != null  and level != ''"> and level = #{level}</if>
            <if test="type != null  and type != ''"> and type = #{type}</if>
            <if test="falseReason != null  and falseReason != ''"> and false_reason = #{falseReason}</if>
        </where>
	 */
	@Override
	public Page<TbClueTrackRecord> selectTbClueTrackRecordList(TbClueTrackRecordDto dto) {
		return this.page(new Page(dto.getPageNum(),dto.getPageSize()),
				new LambdaQueryWrapper<TbClueTrackRecord>().
				eq(StringUtils.isNotEmpty(dto.getClueId()),TbClueTrackRecord::getClueId, dto.getClueId()).
				eq(StringUtils.isNotEmpty(dto.getSubject()),TbClueTrackRecord::getSubject, dto.getSubject()).
				eq(StringUtils.isNotEmpty(dto.getRecord()),TbClueTrackRecord::getRecord, dto.getRecord()).
				eq(StringUtils.isNotEmpty(dto.getLevel()),TbClueTrackRecord::getLevel, dto.getLevel()).
				eq(StringUtils.isNotEmpty(dto.getType()),TbClueTrackRecord::getType, dto.getType()).
				eq(StringUtils.isNotEmpty(dto.getFalseReason()),TbClueTrackRecord::getFalseReason, dto.getFalseReason())
				);
//		return tbClueTrackRecordMapper.selectTbClueTrackRecordList(dto);
	}

	/**
	 * 新增线索跟进记录
	 * 
	 * @param tbClueTrackRecord 线索跟进记录
	 * @return 结果
	 */
	@Override
	public int insertTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord) {
		tbClueTrackRecord.setCreateTime(DateUtils.getNowDate());
		tbClueTrackRecord.setCreateBy(SecurityUtils.getUsername());
		return this.save(tbClueTrackRecord)&&true?1:0;
//		return tbClueTrackRecordMapper.insertTbClueTrackRecord(tbClueTrackRecord);
	}

	@Override
	@Transactional
	public int insertTbClueTrackRecord(TbClue tbClue, TbClueTrackRecord tbClueTrackRecord) {
		tbClueMapper.updateTbClue(tbClue);
		return this.save(tbClueTrackRecord)&&true?1:0;
	}

	/**
	 * 修改线索跟进记录
	 * 
	 * @param tbClueTrackRecord 线索跟进记录
	 * @return 结果
	 */
	@Override
	public int updateTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord) {
		return tbClueTrackRecordMapper.updateTbClueTrackRecord(tbClueTrackRecord);
	}

	/**
	 * 批量删除线索跟进记录
	 * 
	 * @param ids 需要删除的线索跟进记录ID
	 * @return 结果
	 */
	@Override
	public int deleteTbClueTrackRecordByIds(Long[] ids) {
		return tbClueTrackRecordMapper.deleteTbClueTrackRecordByIds(ids);
	}

	/**
	 * 删除线索跟进记录信息
	 * 
	 * @param id 线索跟进记录ID
	 * @return 结果
	 */
	@Override
	public int deleteTbClueTrackRecordById(Long id) {
		return tbClueTrackRecordMapper.deleteTbClueTrackRecordById(id);
	}
}
