package com.huike.business.service;

import java.util.List;

import com.huike.business.domain.TbBusiness;
import com.huike.business.domain.TbBusinessTrackRecord;

/**
 * 商机跟进记录Service接口
 * 
 * @date 2021-04-28
 */
public interface ITbBusinessTrackRecordService {

	/**
	 * 查询商机跟进记录列表
	 * 
	 * @param tbBusinessTrackRecord 商机跟进记录
	 * @return 商机跟进记录集合
	 */
	public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordList(TbBusinessTrackRecord tbBusinessTrackRecord);

	/**
	 * 修改商机跟进记录
	 * 
	 * @param tbBusinessTrackRecord 商机跟进记录
	 * @return 结果
	 */
	public int updateTbBusinessTrackRecord(TbBusinessTrackRecord tbBusinessTrackRecord);

	/**
	 * 批量删除商机跟进记录
	 * 
	 * @param ids 需要删除的商机跟进记录ID
	 * @return 结果
	 */
	public int deleteTbBusinessTrackRecordByIds(Long[] ids);

	/**
	 * 删除商机跟进记录信息
	 * 
	 * @param id 商机跟进记录ID
	 * @return 结果
	 */
	public int deleteTbBusinessTrackRecordById(Long id);

}
