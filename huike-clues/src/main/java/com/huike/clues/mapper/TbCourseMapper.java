package com.huike.clues.mapper;

import java.util.List;
import com.huike.clues.domain.TbCourse;

/**
 * 课程管理Mapper接口
 * 
 * @author wgl
 * @date 2021-04-01
 */
public interface TbCourseMapper 
{
    /**
     * 查询课程管理
     * 
     * @param id 课程管理ID
     * @return 课程管理
     */
    public TbCourse selectTbCourseById(Long id);

    /**
     * 查询课程管理列表
     * 
     * @param tbCourse 课程管理
     * @return 课程管理集合
     */
    public List<TbCourse> selectTbCourseList(TbCourse tbCourse);

    /**
     * 新增课程管理
     * 
     * @param tbCourse 课程管理
     * @return 结果
     */
    public int insertTbCourse(TbCourse tbCourse);

    /**
     * 修改课程管理
     * 
     * @param tbCourse 课程管理
     * @return 结果
     */
    public int updateTbCourse(TbCourse tbCourse);

    /**
     * 删除课程管理
     * 
     * @param id 课程管理ID
     * @return 结果
     */
    public int deleteTbCourseById(Long id);

    /**
     * 批量删除课程管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbCourseByIds(Long[] ids);
}
