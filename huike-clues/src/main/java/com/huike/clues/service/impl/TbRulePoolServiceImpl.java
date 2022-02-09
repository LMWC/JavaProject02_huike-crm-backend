package com.huike.clues.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.mapper.TbRulePoolMapper;
import com.huike.clues.mapper.mybatis_plus.TbRulePoolPlusMapper;
import com.huike.clues.service.ITbRulePoolService;

/**
 * 线索池规则Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-04-16
 */
@Service
public class TbRulePoolServiceImpl implements ITbRulePoolService {
	
	@Autowired
	private TbRulePoolMapper tbRulePoolMapper;
	
	@Autowired
	private TbRulePoolPlusMapper tbRulePoolPlusMapper;


	/**
	 * 查询线索池规则
	 * 
	 * @param id 线索池规则ID
	 * @return 线索池规则
	 */
	@Override
	public TbRulePool selectTbRulePoolById(Long id) {
		return tbRulePoolPlusMapper.selectById(id);
//		return tbRulePoolMapper.selectTbRulePoolById(id);
	}

	@Override
	public TbRulePool selectTbRulePoolByType(String type) {
		return tbRulePoolPlusMapper.selectOne(new LambdaQueryWrapper<TbRulePool>().eq(TbRulePool::getType, type));
//		return tbRulePoolMapper.selectTbRulePoolByType(type);
	}

	/**
	 * 查询线索池规则列表
	 * 
	 * @param tbRulePool 线索池规则
	 * @return 线索池规则
	 */
	@Override
	public List<TbRulePool> selectTbRulePoolList(TbRulePool tbRulePool) {
		LambdaQueryWrapper<TbRulePool> queryWrapper = getRulePoolLambdaQueryWrapper(tbRulePool);
		return tbRulePoolPlusMapper.selectList(queryWrapper);
//		return tbRulePoolMapper.selectTbRulePoolList(tbRulePool);
	}

	/**
	 * 新增线索池规则
	 * 
	 * @param tbRulePool 线索池规则
	 * @return 结果
	 */
	@Override
	public int insertTbRulePool(TbRulePool tbRulePool) {
		return tbRulePoolPlusMapper.insert(tbRulePool);
//		return tbRulePoolMapper.insertTbRulePool(tbRulePool);
	}

	/**
	 * 修改线索池规则
	 * 
	 * @param tbRulePool 线索池规则
	 * @return 结果
	 */
	@Override
	public int updateTbRulePool(TbRulePool tbRulePool) {
		return tbRulePoolPlusMapper.update(tbRulePool, getRulePoolLambdaUpdateWrapper(tbRulePool));
	}

	/**
	 * 批量删除线索池规则
	 * 
	 * @param ids 需要删除的线索池规则ID
	 * @return 结果
	 */
	@Override
	public int deleteTbRulePoolByIds(Long[] ids) {
		return tbRulePoolPlusMapper.delete(new LambdaQueryWrapper<TbRulePool>().in(TbRulePool::getId, ids));
//		return tbRulePoolMapper.deleteTbRulePoolByIds(ids);
	}

	/**
	 * 删除线索池规则信息
	 * 
	 * @param id 线索池规则ID
	 * @return 结果
	 */
	@Override
	public int deleteTbRulePoolById(Long id) {
		return tbRulePoolPlusMapper.delete(new LambdaQueryWrapper<TbRulePool>().eq(TbRulePool::getId, id));
//		return tbRulePoolMapper.deleteTbRulePoolById(id);
	}
	
	/**
	 * 获取规则池查询条件对象
	 * @param tbRulePool
	 * @return
	 */
	private LambdaQueryWrapper<TbRulePool> getRulePoolLambdaQueryWrapper(TbRulePool tbRulePool){
		LambdaQueryWrapper<TbRulePool> queryWrapper = new LambdaQueryWrapper<TbRulePool>();
		//LimitTime
		queryWrapper.eq((tbRulePool.getLimitTime()!=null&&StringUtils.isNotEmpty(tbRulePool.getLimitTime()+"")),
				TbRulePool::getLimitTime, 
				tbRulePool.getLimitTime());
		//limitTimeType
		queryWrapper.eq((tbRulePool.getLimitTimeType()!=null&&StringUtils.isNotEmpty(tbRulePool.getLimitTimeType()+"")),
				TbRulePool::getLimitTimeType, 
				tbRulePool.getLimitTimeType());
		//warnTime
		queryWrapper.eq((tbRulePool.getWarnTime()!=null&&StringUtils.isNotEmpty(tbRulePool.getWarnTime()+"")),
				TbRulePool::getWarnTime, 
				tbRulePool.getWarnTime());
		//warnTimeType
		queryWrapper.eq((tbRulePool.getWarnTimeType()!=null&&StringUtils.isNotEmpty(tbRulePool.getWarnTimeType()+"")),
				TbRulePool::getWarnTimeType, 
				tbRulePool.getWarnTimeType());
		//repeatGetTime
		queryWrapper.eq((tbRulePool.getRepeatGetTime()!=null&&StringUtils.isNotEmpty(tbRulePool.getRepeatGetTime()+"")),
				TbRulePool::getRepeatGetTime, 
				tbRulePool.getRepeatGetTime());
		//repeatType
		queryWrapper.eq((tbRulePool.getRepeatType()!=null&&StringUtils.isNotEmpty(tbRulePool.getRepeatType()+"")),
				TbRulePool::getRepeatType, 
				tbRulePool.getRepeatType());
		//maxNunmber
		queryWrapper.eq((tbRulePool.getMaxNunmber()!=null&&StringUtils.isNotEmpty(tbRulePool.getMaxNunmber()+"")),
				TbRulePool::getMaxNunmber, 
				tbRulePool.getMaxNunmber());
		//type
		queryWrapper.eq((tbRulePool.getType()!=null&&StringUtils.isNotEmpty(tbRulePool.getType()+"")),
				TbRulePool::getType, 
				tbRulePool.getType());
		return queryWrapper;
	}
	
	/**
	 * 获取线索规则池修改对象
	 * @param tbRulePool
	 * @return
	 */
	private LambdaUpdateWrapper<TbRulePool> getRulePoolLambdaUpdateWrapper(TbRulePool tbRulePool){
		LambdaUpdateWrapper<TbRulePool> updateWrapper = new LambdaUpdateWrapper<TbRulePool>();
		//id
		updateWrapper.eq(TbRulePool::getId,tbRulePool.getId());
		//LimitTime
		updateWrapper.set((tbRulePool.getLimitTime()!=null&&StringUtils.isNotEmpty(tbRulePool.getLimitTime()+"")),
				TbRulePool::getLimitTime, 
				tbRulePool.getLimitTime());
		//limitTimeType
		updateWrapper.set((tbRulePool.getLimitTimeType()!=null&&StringUtils.isNotEmpty(tbRulePool.getLimitTimeType()+"")),
				TbRulePool::getLimitTimeType, 
				tbRulePool.getLimitTimeType());
		//warnTime
		updateWrapper.set((tbRulePool.getWarnTime()!=null&&StringUtils.isNotEmpty(tbRulePool.getWarnTime()+"")),
				TbRulePool::getWarnTime, 
				tbRulePool.getWarnTime());
		//warnTimeType
		updateWrapper.set((tbRulePool.getWarnTimeType()!=null&&StringUtils.isNotEmpty(tbRulePool.getWarnTimeType()+"")),
				TbRulePool::getWarnTimeType, 
				tbRulePool.getWarnTimeType());
		//repeatGetTime
		updateWrapper.set((tbRulePool.getRepeatGetTime()!=null&&StringUtils.isNotEmpty(tbRulePool.getRepeatGetTime()+"")),
				TbRulePool::getRepeatGetTime, 
				tbRulePool.getRepeatGetTime());
		//repeatType
		updateWrapper.set((tbRulePool.getRepeatType()!=null&&StringUtils.isNotEmpty(tbRulePool.getRepeatType()+"")),
				TbRulePool::getRepeatType, 
				tbRulePool.getRepeatType());
		//maxNunmber
		updateWrapper.set((tbRulePool.getMaxNunmber()!=null&&StringUtils.isNotEmpty(tbRulePool.getMaxNunmber()+"")),
				TbRulePool::getMaxNunmber, 
				tbRulePool.getMaxNunmber());
		//type
		updateWrapper.set((tbRulePool.getType()!=null&&StringUtils.isNotEmpty(tbRulePool.getType()+"")),
				TbRulePool::getType, 
				tbRulePool.getType());
		return updateWrapper;
	}
}
