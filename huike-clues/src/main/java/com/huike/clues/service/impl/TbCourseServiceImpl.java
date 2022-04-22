package com.huike.clues.service.impl;

import java.util.List;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huike.clues.mapper.TbCourseMapper;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.service.ITbCourseService;

/**
 * 课程管理Service业务层处理
 * 
 * @author wgl
 * @date 2021-04-01
 */
@Service
public class TbCourseServiceImpl implements ITbCourseService 
{
    @Autowired
    private TbCourseMapper tbCourseMapper;

    /**
     * 查询课程管理
     * 
     * @param id 课程管理ID
     * @return 课程管理
     */
    @Override
    public TbCourse selectTbCourseById(Long id)
    {
        return tbCourseMapper.selectTbCourseById(id);
    }

    /**
     * 查询课程管理列表
     * 
     * @param tbCourse 课程管理
     * @return 课程管理
     */
    @Override
    public List<TbCourse> selectTbCourseList(TbCourse tbCourse)
    {
        return tbCourseMapper.selectTbCourseList(tbCourse);
    }

    /**
     * 新增课程管理
     * 
     * @param tbCourse 课程管理
     * @return 结果
     */
    @Override
    public int insertTbCourse(TbCourse tbCourse)
    {
        tbCourse.setCreateTime(DateUtils.getNowDate());
        tbCourse.setCode(StringUtils.getRandom(8));
        return tbCourseMapper.insertTbCourse(tbCourse);
    }

    /**
     * 修改课程管理
     * 
     * @param tbCourse 课程管理
     * @return 结果
     */
    @Override
    public int updateTbCourse(TbCourse tbCourse)
    {
        return tbCourseMapper.updateTbCourse(tbCourse);
    }

    /**
     * 批量删除课程管理
     * 
     * @param ids 需要删除的课程管理ID
     * @return 结果
     */
    @Override
    public int deleteTbCourseByIds(Long[] ids)
    {
        return tbCourseMapper.deleteTbCourseByIds(ids);
    }

    /**
     * 删除课程管理信息
     * 
     * @param id 课程管理ID
     * @return 结果
     */
    @Override
    public int deleteTbCourseById(Long id)
    {
        return tbCourseMapper.deleteTbCourseById(id);
    }
}
