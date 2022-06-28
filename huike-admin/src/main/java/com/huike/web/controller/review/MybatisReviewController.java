package com.huike.web.controller.review;


import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.review.pojo.Review;
import com.huike.review.service.ReviewService;
import com.huike.review.vo.MybatisReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 保存数据的方法
     * 同学需要将对应的方法补全，用于检验同学对三层架构的熟悉程度
     * 判断学员能否独立编写接口
     * @param name  姓名
     * @param age   年龄
     * @param name  性别（传入男,女）
     * @return
     */
    @GetMapping("/saveData/{name}/{age}/{sex}")
    public AjaxResult saveData(@PathVariable("name") String name,
                               @PathVariable("age") String age,
                               @PathVariable("sex")String sex){
        return AjaxResult.success(reviewService.saveData(name,age,sex));
    }

    /**
     * 保存数据的方法
     * 同学需要将对应的方法补全，用于检验同学对三层架构的熟悉程度
     * 判断学员能否独立编写接口
     * 注：
     * @param reviewVO 用户对象
     * @return
     */
    @PostMapping("/saveData")
    public AjaxResult saveData(@RequestBody MybatisReviewVO reviewVO){
        return AjaxResult.success(reviewService.saveData(reviewVO));
    }

    /**=========================================================删除数据=============================================*/

    /**
     * 删除数据的方法
     * @param id 传入主键id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    public AjaxResult removeId(@PathVariable("id")Long id){
        return AjaxResult.success(reviewService.removeData(id));
    }

    /**=========================================================修改数据=============================================*/

    /**
     * 修改数据的方法
     * @param reviewVO 用户对象
     * @return
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody MybatisReviewVO reviewVO){
        return AjaxResult.success(reviewService.updateData(reviewVO));
    }

    /**=========================================================查询数据=============================================*/

    /**
     * 根据id查询对应数据的方法
     * 同学需要将对应的方法补全，用于检验同学对三层架构的熟悉程度
     * 判断学员能否独立编写接口
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public AjaxResult getDataById(@RequestParam("id") Long id){
        return AjaxResult.success(reviewService.getDataById(id));
    }

    /**
     * 分页查询数据的方法
     * @return
     */
    @GetMapping("/getDataByPage")
    public TableDataInfo getDataByPage(){
        startPage();
        List<Review> list = reviewService.getDataByPage();
        return getDataTable(list);
    }

}