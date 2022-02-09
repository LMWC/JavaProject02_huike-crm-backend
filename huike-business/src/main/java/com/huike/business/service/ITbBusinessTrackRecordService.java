package com.huike.business.service;

import java.util.List;

import com.huike.business.domain.TbBusiness;
import com.huike.business.domain.TbBusinessTrackRecord;
import com.huike.business.dto.TbBusinessTrackRecordDto;

/**
 * 商机跟进记录Service接口
 * @date 2021-04-28
 */
public interface ITbBusinessTrackRecordService 
{
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



    public int insertTbBusinessTrackRecord(TbBusiness tbBusiness,TbBusinessTrackRecord tbBusinessTrackRecord);



    
    /**
     * 商机根据记录
     * @param tbBusinessTrackRecord
     * @return
     */
	public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordList(TbBusinessTrackRecordDto tbBusinessTrackRecord);
}
