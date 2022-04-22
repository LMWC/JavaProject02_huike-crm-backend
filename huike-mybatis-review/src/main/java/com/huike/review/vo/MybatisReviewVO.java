package com.huike.review.vo;


import lombok.Data;

/**
 * 用户VO类
 * 所谓的VO即ViewObject
 * 前端往后端传递的对象和后端返回前端的对象都叫VO
 * 即前端的可视化对象，用于给前端返回数据
 */
@Data
public class MybatisReviewVO {

    private Long id;

    private String name;

    private Integer age;

    private String sex;
}
