package com.huike.review.pojo;


import com.huike.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * mybatis_review的实体类
 * 注意：该类与咱们的系统无关，主要是辅助学员来熟悉三层架构和mybatis的使用的
 */
@Data
public class Review extends BaseEntity {

    private Long id;

    private String name;

    private Integer age;

    private String sex;

}
