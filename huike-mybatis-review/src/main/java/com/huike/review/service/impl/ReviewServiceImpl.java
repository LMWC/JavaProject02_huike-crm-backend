package com.huike.review.service.impl;

import com.huike.common.core.domain.AjaxResult;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.bean.BeanUtils;
import com.huike.review.pojo.Review;
import com.huike.review.service.ReviewService;
import com.huike.review.mapper.MybatisReviewMapper;
import com.huike.review.vo.MybatisReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * mybatis复习使用的业务层
 * 注意该业务层和我们系统的业务无关，主要是让同学们快速熟悉系统的
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private MybatisReviewMapper reviewMapper;

    /**=========================================================保存数据的方法=============================================*/

    /**
     * 保存数据的方法
     * 需要结合异常处理
     * @param name
     * @param age
     * @param sex
     * @return
     */
    @Override
    public String saveData(String name, String age, String sex) {
        try {
            int i = reviewMapper.saveData(name, age, sex);
            //这里演示全局异常处理 GlobalExceptionHandler 对指定异常的处理
            //throw new Exception();
            return "成功插入:" + i + "条数据";
        }catch (Exception e){
            throw new CustomException("数据插入失败");
        }
    }

    /**
     * 该方法使用对象进行保存数据
     * 在该方法位置可以通过BeanUtils.copyProperties的方式进行对象属性的填充
     * @param reviewVO 前端传入对象
     * @return
     */
    @Override
    public String saveData(MybatisReviewVO reviewVO) {
        try {
            Review review = new Review();
            BeanUtils.copyProperties(reviewVO,review);
            int i = reviewMapper.saveDataByPojo(review);
            //这里演示全局异常处理 GlobalExceptionHandler 对指定异常的处理
            //throw new Exception();
            return "成功插入:" + i + "条数据";
        }catch (Exception e){
            throw new CustomException("数据插入失败");
        }
    }

    /**=========================================================删除数据=============================================*/

    /**
     * 删除数据的方法
     * @param id
     * @return
     */
    @Override
    public String removeData(Long id) {
        try {
            int i = reviewMapper.removeData(id);
            return "成功删除:" + i + "条数据";
        } catch (Exception e) {
            throw new CustomException("数据删除失败");
        }
    }

    /**=========================================================修改数据=============================================*/

    /**
     * 修改数据的方法
     * @param reviewVO
     * @return
     */
    @Override
    public String updateData(MybatisReviewVO reviewVO) {
        try {
            Review review = new Review();
            BeanUtils.copyProperties(reviewVO,review);
            reviewMapper.updateData(review);
            return "修改成功";
        }catch (Exception e){
            throw new CustomException("修改失败");
        }
    }

    /**=========================================================查询数据的方法=============================================*/

    /**
     * 根据id查询数据的方法
     * @param id
     * @return
     */
    @Override
    public Review getDataById(Long id) {
        return reviewMapper.getById(id);
    }

    /**
     * 分页查询数据的方法
     * @return
     */
    @Override
    public List<Review> getDataByPage() {
        return reviewMapper.getDataByPage();
    }

}
