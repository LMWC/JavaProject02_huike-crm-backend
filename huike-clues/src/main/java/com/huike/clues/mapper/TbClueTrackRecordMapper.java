package com.huike.clues.mapper;

import java.util.List;
import com.huike.clues.domain.TbClueTrackRecord;

/**
 * 线索跟进记录Mapper接口
 * 
 * @date 2021-04-19
 */
public interface TbClueTrackRecordMapper {
	/**
	 * 查询线索跟进记录
	 * 
	 * @param id 线索跟进记录ID
	 * @return 线索跟进记录
	 */
	public TbClueTrackRecord selectTbClueTrackRecordById(Long id);

	/**
	 * 查询线索跟进记录列表
	 * 
	 * @param tbClueTrackRecord 线索跟进记录
	 * @return 线索跟进记录集合
	 */
	public List<TbClueTrackRecord> selectTbClueTrackRecordList(TbClueTrackRecord tbClueTrackRecord);

	/**
	 * 新增线索跟进记录
	 * 
	 * @param tbClueTrackRecord 线索跟进记录
	 * @return 结果
	 */
	public int insertTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord);

	/**
	 * 修改线索跟进记录
	 * 
	 * @param tbClueTrackRecord 线索跟进记录
	 * @return 结果
	 */
	public int updateTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord);

	/**
	 * 删除线索跟进记录
	 * 
	 * @param id 线索跟进记录ID
	 * @return 结果
	 */
	public int deleteTbClueTrackRecordById(Long id);

	/**
	 * 批量删除线索跟进记录
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTbClueTrackRecordByIds(Long[] ids);

}
