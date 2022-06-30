package com.huike.business.mapper;

import java.util.List;
import com.huike.business.domain.TbBusinessTrackRecord;

/**
 * 商机跟进记录Mapper接口
 * @date 2021-04-28
 */
public interface TbBusinessTrackRecordMapper {

    /**
     * 跟进商机id查询商机跟进记录
     * @param id
     * @return
     */
    public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordListByBusinessId(Long id);

    /**
     * 新增商机跟进记录
     * @param tbBusinessTrackRecord 商机跟进记录
     * @return 结果
     */
    public int insertTbBusinessTrackRecord(TbBusinessTrackRecord tbBusinessTrackRecord);

}