package com.huike.clues.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysConfig;
import com.huike.clues.mapper.SysConfigMapper;
import com.huike.clues.mapper.mybatis_plus.SysConfigPlusMapper;
import com.huike.clues.service.ISysConfigService;
import com.huike.common.annotation.DataSource;
import com.huike.common.constant.Constants;
import com.huike.common.constant.UserConstants;
import com.huike.common.core.redis.RedisCache;
import com.huike.common.core.text.Convert;
import com.huike.common.enums.DataSourceType;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.StringUtils;

/**
 * 参数配置 服务层实现
 * 
 * 
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigPlusMapper, SysConfig> implements ISysConfigService {
	@Autowired
	private SysConfigMapper configMapper;

	@Autowired
	private RedisCache redisCache;

	/**
	 * 项目启动时，初始化参数到缓存
	 * <if test="configName != null and configName != ''">
				AND config_name like concat('%', #{configName}, '%')
			</if>
			<if test="configType != null and configType != ''">
				AND config_type = #{configType}
			</if>
			<if test="configKey != null and configKey != ''">
				AND config_key like concat('%', #{configKey}, '%')
			</if>
			<if test = "params != null ">
				<if test="params.beginTime != null and params.beginTime != ''"><!-- 开始时间检索 -->
					and date_format(create_time,'%y%m%d') &gt;= date_format(#{params.beginTime},'%y%m%d')
				</if>
				<if test="params.endTime != null and params.endTime != ''"><!-- 结束时间检索 -->
					and date_format(create_time,'%y%m%d') &lt;= date_format(#{params.endTime},'%y%m%d')
				</if>
			</if>
	 */
	@PostConstruct
	public void init() {
		List<SysConfig> configsList = this.list();
//		List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
		for (SysConfig config : configsList) {
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
	}

	/**
	 * 查询参数配置信息
	 * 
	 * @param configId 参数配置ID
	 * @return 参数配置信息
	 */
	@Override
	@DataSource(DataSourceType.MASTER)
	public SysConfig selectConfigById(Long configId) {
		SysConfig config = new SysConfig();
		config.setConfigId(configId);
		/**
		 * SELECT 
			  config_id,config_name,config_key,config_value,config_type,
			  create_by,create_time,update_by,update_time,remark 
			FROM
			  sys_config 
			WHERE 1 = 1 
			  AND config_id = #{configId}
			  AND config_key = #{configKey}
		 */
		return this.getOne(new LambdaQueryWrapper<SysConfig>().
				eq(ObjectUtils.isNotEmpty(config.getConfigId()),SysConfig::getConfigId,config.getConfigId()).
				eq(ObjectUtils.isNotEmpty(config.getConfigKey()),SysConfig::getConfigKey,config.getConfigKey()));
//		return configMapper.selectConfig(config);
	}

	/**
	 * 根据键名查询参数配置信息
	 * 
	 * @param configKey 参数key
	 * @return 参数键值
	 */
	@Override
	public String selectConfigByKey(String configKey) {
		String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
		if (StringUtils.isNotEmpty(configValue)) {
			return configValue;
		}
		SysConfig config = new SysConfig();
		config.setConfigKey(configKey);
//		SysConfig retConfig = configMapper.selectConfig(config);
		SysConfig retConfig = this.getOne(new LambdaQueryWrapper<SysConfig>().
				eq(ObjectUtils.isNotEmpty(config.getConfigId()),SysConfig::getConfigId,config.getConfigId()).
				eq(ObjectUtils.isNotEmpty(config.getConfigKey()),SysConfig::getConfigKey,config.getConfigKey()));
		if (StringUtils.isNotNull(retConfig)) {
			redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
			return retConfig.getConfigValue();
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 查询参数配置列表
	 * 
	 * @param config 参数配置信息
	 * @return 参数配置集合
	 * <where>
			<if test="configName != null and configName != ''">
				AND config_name like concat('%', #{configName}, '%')
			</if>
			<if test="configType != null and configType != ''">
				AND config_type = #{configType}
			</if>
			<if test="configKey != null and configKey != ''">
				AND config_key like concat('%', #{configKey}, '%')
			</if>
		</where>
	 */
	@Override
	public List<SysConfig> selectConfigList(SysConfig config) {
		return this.list(new LambdaQueryWrapper<SysConfig>().
				like(StringUtils.isNotEmpty(config.getConfigName()),SysConfig::getConfigName,config.getConfigName()).
				eq(StringUtils.isNotEmpty(config.getConfigType()),SysConfig::getConfigType, config.getConfigType()).
				like(StringUtils.isNotEmpty(config.getConfigKey()),SysConfig::getConfigType, config.getConfigKey())
				);
//		return configMapper.selectConfigList(config);
	}
 
	/**
	 * 新增参数配置
	 * 
	 * @param config 参数配置信息
	 * @return 结果
	 */
	@Override
	public int insertConfig(SysConfig config) {
		int row = this.save(config)&&true?1:0;
//		int row = configMapper.insertConfig(config);
		if (row > 0) {
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return row;
	}

	/**
	 * 修改参数配置
	 * 
	 * @param config 参数配置信息
	 * @return 结果
	 */
	@Override
	public int updateConfig(SysConfig config) {
		int row = configMapper.updateConfig(config);
		if (row > 0) {
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return row;
	}

	/**
	 * 批量删除参数信息
	 * 
	 * @param configIds 需要删除的参数ID
	 * @return 结果
	 */
	@Override
	public int deleteConfigByIds(Long[] configIds) {
		for (Long configId : configIds) {
			SysConfig config = selectConfigById(configId);
			if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
				throw new CustomException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
			}
		}
		boolean flag = this.remove(new LambdaQueryWrapper<SysConfig>().
				in(CollectionUtils.isNotEmpty(Arrays.asList(configIds)),SysConfig::getConfigId, configIds));
//		int count = configMapper.deleteConfigByIds(configIds);
//		if (count > 0) {
		if(flag) {
			Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
			redisCache.deleteObject(keys);
			return configIds.length;
		}else {
			return 0;
		}
	}

	/**
	 * 清空缓存数据
	 */
	@Override
	public void clearCache() {
		Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
		redisCache.deleteObject(keys);
	}

	/**
	 * 校验参数键名是否唯一
	 * 
	 * @param config 参数配置信息
	 * @return 结果
	 */
	@Override
	public String checkConfigKeyUnique(SysConfig config) {
		Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
		SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
		if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 设置cache key
	 * 
	 * @param configKey 参数键
	 * @return 缓存键key
	 */
	private String getCacheKey(String configKey) {
		return Constants.SYS_CONFIG_KEY + configKey;
	}
}
