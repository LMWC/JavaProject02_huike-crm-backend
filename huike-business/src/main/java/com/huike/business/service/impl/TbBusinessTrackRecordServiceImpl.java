package com.huike.business.service.impl;

import java.util.List;

import com.huike.business.domain.TbBusiness;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.service.ISysDictDataService;
import com.huike.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huike.business.mapper.TbBusinessTrackRecordMapper;
import com.huike.business.domain.TbBusinessTrackRecord;
import com.huike.business.service.ITbBusinessTrackRecordService;

/**
 * 商机跟进记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-04-28
 */
@Service
public class TbBusinessTrackRecordServiceImpl implements ITbBusinessTrackRecordService {
    @Autowired
    private TbBusinessTrackRecordMapper tbBusinessTrackRecordMapper;

    @Autowired
    private TbBusinessMapper tbBusinessMapper;



    /**
     * 查询商机跟进记录列表
     * 
     * @param tbBusinessTrackRecord 商机跟进记录
     * @return 商机跟进记录
     */
    @Override
    public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordList(TbBusinessTrackRecord tbBusinessTrackRecord){
        return tbBusinessTrackRecordMapper.selectTbBusinessTrackRecordList(tbBusinessTrackRecord);
    }


    /**
     * 修改商机跟进记录
     * 
     * @param tbBusinessTrackRecord 商机跟进记录
     * @return 结果
     */
    @Override
    public int updateTbBusinessTrackRecord(TbBusinessTrackRecord tbBusinessTrackRecord)
    {
        return tbBusinessTrackRecordMapper.updateTbBusinessTrackRecord(tbBusinessTrackRecord);
    }

    /**
     * 批量删除商机跟进记录
     * 
     * @param ids 需要删除的商机跟进记录ID
     * @return 结果
     */
    @Override
    public int deleteTbBusinessTrackRecordByIds(Long[] ids)
    {
        return tbBusinessTrackRecordMapper.deleteTbBusinessTrackRecordByIds(ids);
    }

    /**
     * 删除商机跟进记录信息
     * 
     * @param id 商机跟进记录ID
     * @return 结果
     */
    @Override
    public int deleteTbBusinessTrackRecordById(Long id)
    {
        return tbBusinessTrackRecordMapper.deleteTbBusinessTrackRecordById(id);
    }
    
}
