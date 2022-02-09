package com.huike.clues.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.huike.common.utils.uuid.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.dto.TbActivityDto;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.clues.mapper.mybatis_plus.TbActivityPlusMapper;
import com.huike.clues.service.ITbActivityService;
import com.huike.common.constant.Constants;
import com.huike.common.core.redis.RedisCache;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.StringUtils;

/**
 * 活动管理Service业务层处理
 * 
 * @date 2021-04-01
 */
@Service
public class TbActivityServiceImpl  extends ServiceImpl<TbActivityPlusMapper,TbActivity> implements ITbActivityService {
	@Autowired
	private TbActivityMapper tbActivityMapper;

	@Autowired
	private TbActivityPlusMapper tbActivityPlusMapper;
	
	@Autowired
	private RedisCache redisCache;

	/**
	 * 查询活动管理
	 * 
	 * @param id 活动管理ID
	 * @return 活动管理
	 */
	@Override
	public TbActivity selectTbActivityById(Long id) {
		LambdaQueryWrapper<TbActivity> queryWrapper = new LambdaQueryWrapper<TbActivity>();
		queryWrapper.eq(TbActivity::getId, id);
		return this.getOne(queryWrapper);
	}

	@Override
	public void loadAllActivityCode() {
		Set<String> codeSet = this.list().
				stream().
				map(TbActivity::getCode).
				collect(Collectors.toSet());
		redisCache.setCacheSet(Constants.ACT_CODE_KEY, codeSet);
	}

	@Override
	public TbActivity selectTbActivityByCode(String code) {
		LambdaQueryWrapper<TbActivity> queryWrapper = new LambdaQueryWrapper<TbActivity>();
		queryWrapper.eq(TbActivity::getCode,code);
		return tbActivityPlusMapper.selectOne(queryWrapper);
//		return tbActivityMapper.selectTbActivityByCode(code);
	}

<<<<<<< HEAD
//    public void loadAllActivityCode() {
//        List<String> codeList= tbActivityMapper.selectAllCode();
//        Set<String> set= new HashSet<>(codeList);
//        redisCache.setCacheSet(Constants.ACT_CODE_KEY, set);
//    }
=======
	/**
	 * 查询活动管理列表
	 * 
	 * @param tbActivity 活动管理
	 * @return 活动管理
	 */
	@Override
	public Page<TbActivity> selectTbActivityList(TbActivityDto tbActivity) {
		return this.page(new Page<TbActivity>(tbActivity.getPageNum(),
				tbActivity.getPageSize()),
				getTbActivityQueryWrapper(tbActivity));
	}
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

	/**
	 * 新增活动管理
	 * 
	 * @param tbActivity 活动管理
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertTbActivity(TbActivity tbActivity) {
		tbActivity.setCreateTime(DateUtils.getNowDate());
		tbActivity.setCode(getCode());
		tbActivity.setStatus("2");
		int rows = tbActivityPlusMapper.insert(tbActivity);
		// 结束任务添加定时任务
		return rows;
	}

<<<<<<< HEAD
    /**
     * 查询活动管理列表
     * 
     * @param tbActivity 活动管理
     * @return 活动管理
     */
    @Override
    public List<TbActivity> selectTbActivityList(TbActivity tbActivity) {
        return tbActivityMapper.selectTbActivityList(tbActivity);
    }

    /**
     * 新增活动管理
     * 
     * @param tbActivity 活动管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertTbActivity(TbActivity tbActivity){
        tbActivity.setCreateTime(DateUtils.getNowDate());
        tbActivity.setCode(UUIDUtils.getUUID());
        tbActivity.setStatus("2");
        int rows= tbActivityMapper.insertTbActivity(tbActivity);
        return rows;
    }
=======
	private String getCode() {
		// 随机8位编码
		String code = StringUtils.getRandom(8);
		// 店铺校验
		Set<String> codeSets = redisCache.getCacheSet(Constants.ACT_CODE_KEY);
		if (codeSets.contains(code)) {
			getCode();
		}
		return code;
	}

	/**
	 * 修改活动管理
	 * 
	 * @param tbActivity 活动管理
	 * @return 结果
	 */
	@Override
	public int updateTbActivity(TbActivity tbActivity) {
		if(tbActivity==null) {
			throw new RuntimeException("数据不合法");
		}
		LambdaQueryWrapper<TbActivity> queryWrapper = new LambdaQueryWrapper<TbActivity>();
		queryWrapper.eq(TbActivity::getId,tbActivity.getId());
		TbActivity dbActivity = tbActivityPlusMapper.selectOne(queryWrapper);
		if(dbActivity==null) {
			throw new RuntimeException("数据不存在");
		}
		LambdaUpdateWrapper<TbActivity> updateWrapper = new LambdaUpdateWrapper<TbActivity>();
		//姓名
		updateWrapper.set(
				tbActivity.getName()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getName()),
				TbActivity::getName,
				tbActivity.getName());
		//channel
		updateWrapper.set(
				tbActivity.getChannel()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getChannel()),
				TbActivity::getChannel,
				tbActivity.getChannel());
		//info
		updateWrapper.set(
				tbActivity.getInfo()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getInfo()),
				TbActivity::getInfo,
				tbActivity.getInfo());
		//type
		updateWrapper.set(
				tbActivity.getType()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getType()),
				TbActivity::getType,
				tbActivity.getType());
		//discount
		updateWrapper.set(
				tbActivity.getDiscount()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getDiscount()+""),
				TbActivity::getDiscount,
				tbActivity.getDiscount());
		//vouchers
		updateWrapper.set(
				tbActivity.getVouchers()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getVouchers()+""),
				TbActivity::getVouchers,
				tbActivity.getVouchers());
		//status
		updateWrapper.set(
				tbActivity.getStatus()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getStatus()+""),
				TbActivity::getStatus,
				tbActivity.getStatus());
		//createTime
		updateWrapper.set(
				tbActivity.getCreateTime()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getCreateTime()+""),
				TbActivity::getCreateTime,
				tbActivity.getCreateTime());
		//beginTime
		updateWrapper.set(
				tbActivity.getBeginTime()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getBeginTime()+""),
				TbActivity::getBeginTime,
				tbActivity.getBeginTime());
		//endTime
		updateWrapper.set(
				tbActivity.getEndTime()!=null
							&&
				StringUtils.isNotEmpty(tbActivity.getEndTime()+""),
				TbActivity::getEndTime,
				tbActivity.getEndTime());
		updateWrapper.eq(TbActivity::getId, dbActivity.getId());
		int rows = tbActivityPlusMapper.update(tbActivity, updateWrapper);
		return rows;
	}
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

	/**
	 * 批量删除活动管理
	 * 
	 * @param ids 需要删除的活动管理ID
	 * @return 结果
	 */
	@Override
	public int deleteTbActivityByIds(Long[] ids) {
		LambdaQueryWrapper<TbActivity> queryWrapper = new LambdaQueryWrapper<TbActivity>();
		queryWrapper.in(TbActivity::getId, ids);
		return this.remove(queryWrapper)&&true ? ids.length : 0;
	}

<<<<<<< HEAD
//    private String getCode(){
//        //随机8位编码
////        String code= StringUtils.getRandom(8);
//        String code = UUIDUtils.getUUID();
//        //店铺校验
//        Set<String> codeSets =  redisCache.getCacheSet(Constants.ACT_CODE_KEY);
//        if(codeSets.contains(code)){
//            getCode();
//        }
//        return code;
//    }



    /**
     * 修改活动管理
     * 
     * @param tbActivity 活动管理
     * @return 结果
     */
    @Override
    public int updateTbActivity(TbActivity tbActivity){
        TbActivity dbActivity= tbActivityMapper.selectTbActivityById(tbActivity.getId());
        int rows= tbActivityMapper.updateTbActivity(tbActivity);
        //结束时间修改任务
        if(tbActivity.getEndTime()!=null&&!tbActivity.getEndTime().equals(dbActivity.getEndTime())){
            String target="activityTask.finish('"+tbActivity.getId()+"')";
            String jobName="活动结束任务id_"+tbActivity.getId();
          //TODO 添加任务
//            sysJobService.addJob(jobName,target,tbActivity.getEndTime());
        }
        return rows;
    }

    /**
     * 批量删除活动管理
     * 
     * @param ids 需要删除的活动管理ID
     * @return 结果
     */
    @Override
    public int deleteTbActivityByIds(Long[] ids)
    {
        return tbActivityMapper.deleteTbActivityByIds(ids);
    }

    /**
     * 删除活动管理信息
     * 
     * @param id 活动管理ID
     * @return 结果
     */
    @Override
    public int deleteTbActivityById(Long id)
    {
        TbActivity tbActivity = tbActivityMapper.selectTbActivityById(id);
        int rows=tbActivityMapper.deleteTbActivityById(id);
      //TODO 添加任务
=======
	/**
	 * 删除活动管理信息
	 * 
	 * @param id 活动管理ID
	 * @return 结果
	 */
	@Override
	public int deleteTbActivityById(Long id) {
		TbActivity tbActivity = tbActivityMapper.selectTbActivityById(id);
		int rows = tbActivityMapper.deleteTbActivityById(id);
		// TODO 添加任务
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
//        sysJobService.deleteJob(tbActivity.getName());
		return rows;
	}

<<<<<<< HEAD
    @Override
    public Map getCountByStatus() {
        return tbActivityMapper.getCountByStatus();
    }

=======
	@Override
	public Map getCountByStatus() {
		return tbActivityMapper.getCountByStatus();
	}
	
	private LambdaQueryWrapper<TbActivity> getTbActivityQueryWrapper(TbActivityDto tbActivity) {
		LambdaQueryWrapper<TbActivity> queryWrapper = new LambdaQueryWrapper<TbActivity>();
		//==========================eq================
		//channel
		queryWrapper.eq(tbActivity.getChannel()!=null && StringUtils.isNotEmpty(tbActivity.getChannel()+""),TbActivity::getChannel,tbActivity.getChannel());
		//code
		queryWrapper.like(tbActivity.getCode()!=null && StringUtils.isNotEmpty(tbActivity.getCode()+""),TbActivity::getCode,tbActivity.getCode());
		//=================between===========
		//beginCreateTime endCreateTime
		queryWrapper.between((tbActivity.getBeginCreateTime()!=null && StringUtils.isNotEmpty(tbActivity.getBeginCreateTime()+""))
				&&
				(tbActivity.getEndCreateTime()!=null && StringUtils.isNotEmpty(tbActivity.getEndCreateTime()+"")), 
				TbActivity::getCreateTime, tbActivity.getBeginCreateTime(), tbActivity.getEndCreateTime());
		//beginTime endTime
		queryWrapper.between((tbActivity.getBeginTime()!=null && StringUtils.isNotEmpty(tbActivity.getBeginTime()+""))
				&&
				(tbActivity.getEndTime()!=null && StringUtils.isNotEmpty(tbActivity.getEndTime()+"")), 
				TbActivity::getBeginTime, tbActivity.getBeginTime(), tbActivity.getEndTime());
		//按照createTime DESC
		queryWrapper.orderByDesc(TbActivity::getCreateTime);
		return queryWrapper;
	}
	
	/**
	 * 获取渠道下所有活动
	 */
	@Override
	public List<TbActivity> selectTbActivityListByChannel(String channel) {
		LambdaQueryWrapper<TbActivity> lambdaQueryWrapper = new LambdaQueryWrapper<TbActivity>();
		if(StringUtils.isNotEmpty(channel)&&!channel.equals("null")) {
			lambdaQueryWrapper.eq(TbActivity::getChannel,channel);
		}
		lambdaQueryWrapper.eq(TbActivity::getStatus,2);
		return this.list(lambdaQueryWrapper);
	}
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
}
