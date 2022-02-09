package com.huike.business.mapper;

import java.util.List;
import com.huike.business.domain.TbBusinessTrackRecord;

/**
 * 商机跟进记录Mapper接口
 * 
 * @date 2021-04-28
 */
public interface TbBusinessTrackRecordMapper {
	/**
	 * 查询商机跟进记录
	 * 
	 * @param id 商机跟进记录ID
	 * @return 商机跟进记录
	 */
	public TbBusinessTrackRecord selectTbBusinessTrackRecordById(Long id);

	/**
	 * 查询商机跟进记录列表
	 * 
	 * @param tbBusinessTrackRecord 商机跟进记录
	 * @return 商机跟进记录集合
	 */
	public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordList(TbBusinessTrackRecord tbBusinessTrackRecord);

	/**
	 * 新增商机跟进记录
	 * 
	 * @param tbBusinessTrackRecord 商机跟进记录
	 * @return 结果
	 */
	public int insertTbBusinessTrackRecord(TbBusinessTrackRecord tbBusinessTrackRecord);

	/**
	 * 修改商机跟进记录
	 * 
	 * @param tbBusinessTrackRecord 商机跟进记录
	 * @return 结果
	 */
	public int updateTbBusinessTrackRecord(TbBusinessTrackRecord tbBusinessTrackRecord);

	/**
	 * 删除商机跟进记录
	 * 
	 * @param id 商机跟进记录ID
	 * @return 结果
	 */
	public int deleteTbBusinessTrackRecordById(Long id);

	/**
	 * 批量删除商机跟进记录
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTbBusinessTrackRecordByIds(Long[] ids);
}
