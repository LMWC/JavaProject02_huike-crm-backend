package com.huike.clues.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huike.clues.domain.SysNotice;
import com.huike.clues.mapper.SysNoticeMapper;
import com.huike.clues.service.ISysNoticeService;

/**
 * 公告 服务层实现
 * 
 * 
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService
{
    @Autowired
    private SysNoticeMapper noticeMapper;

    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }


    /**
     * 消息提醒列表
     * @param noticeUserId
     * @param status=0未读 已读 1
     * @return
     */
    @Override
    public List<SysNotice> selectNoticeList(Long noticeUserId, String status)
    {
        return noticeMapper.selectNoticeList(noticeUserId,status);
    }

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice)
    {
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改公告
     * 
     * @param noticeId 公告信息
     * @return 结果
     */
    @Override
    public int updateNoticeRead(Long noticeId)
    {
        return noticeMapper.updateNoticeRead(noticeId);
    }

    /**
     * 删除公告对象
     * 
     * @param noticeId 公告ID
     * @return 结果
     */
//    @Override
//    public int deleteNoticeById(Long noticeId)
//    {
//        return noticeMapper.deleteNoticeById(noticeId);
//    }
//
//    /**
//     * 批量删除公告信息
//     *
//     * @param noticeIds 需要删除的公告ID
//     * @return 结果
//     */
//    @Override
//    public int deleteNoticeByIds(Long[] noticeIds)
//    {
//        return noticeMapper.deleteNoticeByIds(noticeIds);
//    }
}
