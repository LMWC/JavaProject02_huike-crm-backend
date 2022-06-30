package com.huike.business.service.impl;


import com.huike.business.domain.TbBusiness;
import com.huike.business.domain.TbBusinessTrackRecord;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.business.mapper.TbBusinessTrackRecordMapper;
import com.huike.business.service.ITbBusinessTrackRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商机跟进记录Service业务层处理
 * 
 * @author wgl
 * @date 2021-04-28
 */
@Service
public class TbBusinessTrackRecordServiceImpl implements ITbBusinessTrackRecordService {

    @Autowired
    private TbBusinessMapper tbBusinessMapper;

    @Autowired
    private TbBusinessTrackRecordMapper tbBusinessTrackRecordMapper;

    /**
     * 跟进商机id查询商机跟进记录
     * @param id
     * @return
     */
    @Override
    public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordList(Long id) {
        return tbBusinessTrackRecordMapper.selectTbBusinessTrackRecordListByBusinessId(id);
    }

    /**
     * 新增商机跟进记录
     * @param tbBusiness
     * @param tbBusinessTrackRecord
     * @return
     */
    @Override
    public int insertTbBusinessTrackRecord(TbBusiness tbBusiness, TbBusinessTrackRecord tbBusinessTrackRecord) {
        tbBusinessMapper.updateTbBusiness(tbBusiness);
        return tbBusinessTrackRecordMapper.insertTbBusinessTrackRecord(tbBusinessTrackRecord);
    }

}
