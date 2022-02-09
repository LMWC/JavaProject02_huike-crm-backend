package com.huike.clues.mapper.mybatis_plus;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.TbClue;

/**
 * mybatisPlus mapper层  TbClue 线索 Mybatisplus数据持久层
 * @author WGL
 */
@Repository
public interface TbCluePlusMapper extends BaseMapper<TbClue> {

}