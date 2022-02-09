package com.huike.clues.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.dto.TbCourseDto;
import com.huike.common.core.page.TableDataInfo;

/**
 * 课程管理Service接口
 * 
 * @author ruoyi
 * @date 2021-04-01
 */
public interface ITbCourseService extends IService<TbCourse>
{


    /**
     * 查询课程管理列表
     */
    TableDataInfo list(TbCourseDto dto);

    boolean add(TbCourse tbCourse);
}
