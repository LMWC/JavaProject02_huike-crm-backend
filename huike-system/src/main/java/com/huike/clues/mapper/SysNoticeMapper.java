package com.huike.clues.mapper;

import java.util.List;
import com.huike.clues.domain.SysNotice;
import org.apache.ibatis.annotations.Param;

/**
 * 通知公告表 数据层
 * 
 * 
 */
public interface SysNoticeMapper
{
    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public SysNotice selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
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
     * 公告id
     * @param noticeId
     * @return
     */
    public int updateNoticeRead(@Param("noticeId") Long noticeId);

//    /**
//     * 批量删除公告
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
