package com.huike.contract.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.huike.business.domain.TbBusiness;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.clues.mapper.TbCourseMapper;
import com.huike.clues.service.ISysDictDataService;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.core.redis.RedisCache;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.bean.BeanUtils;
import com.huike.contract.domain.vo.ActivityInstanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huike.contract.mapper.TbContractMapper;
import com.huike.contract.domain.TbContract;
import com.huike.contract.service.ITbContractService;

/**
 * 合同Service业务层处理

 */
@Service
public class TbContractServiceImpl implements ITbContractService 
{
    @Autowired
    private TbContractMapper tbContractMapper;


    @Autowired
    private TbBusinessMapper tbBusinessMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TbActivityMapper activityMapper;

    @Autowired
    private TbCourseMapper courseMapper;



    private ISysDictDataService sysDictDataService;


    @Override
    public TbContract selectTbContractById(Long id) {
        return tbContractMapper.selectTbContractById(id);
    }

    /**
     * 查询合同列表
     */
    @Override
    public List<TbContract> selectTbContract(TbContract queryConditon){
        /**
         * admin不根据创建人查看，即admin查看所有的合同
         */
        if(!SecurityUtils.getAdmin().equals(SecurityUtils.getUserId())){
            //当前用户是admin不设置查询条件
            queryConditon.setCreateBy(SecurityUtils.getUsername());
        }
        List<TbContract> list= tbContractMapper.selectTbContractList(queryConditon);
        return list;
    }



    /**
     * 新增合同
     * 
     * @param tbContract 合同
     * @return 结果
     */
    @Override
    public int insertTbContract(TbContract tbContract)
    {
        String businessKey=""+System.nanoTime();
        tbContract.setId(businessKey);
        tbContract.setCreateBy(SecurityUtils.getUsername());
        tbContract.setCreateTime(DateUtils.getNowDate());
        tbContract.setCreateBy(SecurityUtils.getUsername());
        tbContract.setCreateTime(DateUtils.getNowDate());
        tbContract.setDeptId(SecurityUtils.getDeptId());
        tbContract.setStatus("4");//状态(待审核1，已通过2，已驳回3 全部完成4)
        setOrder(tbContract);
        return tbContractMapper.insertTbContract(tbContract);
    }


    @Override
    public int changeContract(Long businessId, TbContract tbContract) {
        TbBusiness tbBusiness= tbBusinessMapper.selectTbBusinessById(businessId);
        if(tbBusiness.getCourseId()==null){
            throw new CustomException("商机里面没有选择课程，无法转换客户合同");
        }

        tbBusiness.setStatus(TbBusiness.StatusType.TOCUSTOMER.getValue());
        tbBusinessMapper.updateTbBusiness(tbBusiness);

        tbContract.setCreateTime(DateUtils.getNowDate());
        tbContract.setBusinessId(businessId);
        BeanUtils.copyProperties(tbBusiness, tbContract);
        return  insertTbContract(tbContract);
        //return tbContractMapper.insertTbContract(tbContract);
    }

    /**
     * 修改合同
     * 
     * @param tbContract 合同
     * @return 结果
     */
    @Override
    public int updateTbContract(TbContract tbContract){
        tbContract.setStatus("2");
        setOrder(tbContract);
        return tbContractMapper.updateTbContract(tbContract);
    }


    private void setOrder(TbContract tbContract){
        TbCourse course= courseMapper.selectTbCourseById(tbContract.getCourseId());
        tbContract.setCoursePrice(course.getPrice());
        TbActivity tbActivity= activityMapper.selectTbActivityById(tbContract.getActivityId());
        if(tbActivity!=null){
            if("1".equals(tbActivity.getType())){
                tbContract.setDiscountType("课程折扣");
                BigDecimal price = new BigDecimal(course.getPrice());
                BigDecimal disCount = new BigDecimal(tbActivity.getDiscount());
                BigDecimal order = price.multiply(disCount.divide(new BigDecimal(10),2,BigDecimal.ROUND_CEILING));
                tbContract.setOrder(order.floatValue());
//                tbContract.setOrder(course.getPrice()*tbActivity.getDiscount());
            }else{
                //当代金券大于等于合同50% 按照原价
                if(tbActivity.getVouchers()>=course.getPrice()/2){
                    tbContract.setOrder(course.getPrice());
                    tbContract.setDiscountType("代金券大于原课程50%,代金券不可用");
                }else{
                    tbContract.setDiscountType("代金券");
                    tbContract.setOrder(course.getPrice()-tbActivity.getVouchers());
                }
            }
        }else{
            tbContract.setDiscountType("无");
            tbContract.setOrder(course.getPrice());
        }
    }

}