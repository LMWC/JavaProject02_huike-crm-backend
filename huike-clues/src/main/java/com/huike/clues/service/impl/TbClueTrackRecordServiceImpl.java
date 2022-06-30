package com.huike.clues.service.impl;


import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.mapper.TbClueTrackRecordMapper;
import com.huike.clues.service.ITbClueService;
import com.huike.clues.service.ITbClueTrackRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 线索跟进记录Service业务层处理
 * @date 2022-04-22
 */
@Service
public class TbClueTrackRecordServiceImpl implements ITbClueTrackRecordService {

    @Autowired
    private TbClueMapper tbClueMapper;

    @Autowired
    private TbClueTrackRecordMapper tbClueTrackRecordMapper;

    /**
     * 根据线索id查询线索跟进记录
     */
    @Override
    public List<TbClueTrackRecord> selectTbClueTrackRecordList(Long clueId) {
        return tbClueTrackRecordMapper.selectTbClueTrackRecordListByClueId(clueId);
    }

    /**
     * 添加线索跟进记录
     * @param tbClue
     * @param tbClueTrackRecord
     * @return
     */
    @Override
    @Transactional
    public int insertTbClueTrackRecord(TbClue tbClue, TbClueTrackRecord tbClueTrackRecord) {
        tbClueMapper.updateTbClue(tbClue);
        return tbClueTrackRecordMapper.insertTbClueTrackRecord(tbClueTrackRecord);
    }
}
