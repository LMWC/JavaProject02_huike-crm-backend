package com.huike.clues.service;

import java.util.List;
import com.huike.clues.domain.SysNotice;
import org.apache.ibatis.annotations.Param;

/**
 * 公告 服务层
 * 
 * 
 */
public interface ISysNoticeService
{
    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public SysNotice selectNoticeById(Long noticeId);


    /**
     * 查看提醒消息
     * @param noticeUserId
     * @param status=0未读 已读 1
     * @return
     */
    public List<SysNotice> selectNoticeList(@Param("noticeUserId") Long noticeUserId, @Param("status") String status);

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public int insertNotice(SysNotice notice);


    /**
     * 修改已读
     * @param noticeId
     * @return
     */
    public int updateNoticeRead(Long noticeId);

//    /**
//     * 删除公告信息
//     *
//     * @param noticeId 公告ID
//     * @return 结果
//     */
//    public int deleteNoticeById(Long noticeId);
//
//    /**
//     * 批量删除公告信息
//     *
//     * @param noticeIds 需要删除的公告ID
//     * @return 结果
//     */
//    public int deleteNoticeByIds(Long[] noticeIds);
}
