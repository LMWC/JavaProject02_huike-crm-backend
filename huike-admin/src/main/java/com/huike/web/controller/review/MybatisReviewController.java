package com.huike.web.controller.review;


import com.huike.common.core.controller.BaseController;
import com.huike.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 该Controller主要是为了复习三层架构以及Mybatis使用的，该部分接口已经放开权限，可以直接访问
 * 同学们在此处编写接口通过浏览器访问看是否能完成最简单的增删改查
 */
@RestController
@RequestMapping("/review")
public class MybatisReviewController extends BaseController {

    @Autowired
    private ReviewService reviewService;

    /**=========================================================新增数据============================================*/

    /**=========================================================删除数据=============================================*/

    /**=========================================================修改数据=============================================*/

    /**=========================================================查询数据=============================================*/

}