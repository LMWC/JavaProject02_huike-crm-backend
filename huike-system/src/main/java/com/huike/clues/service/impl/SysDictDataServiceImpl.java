package com.huike.clues.service.impl;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.mybatis_plus.SysDictDataPlusMapper;
import com.huike.clues.service.ISysDictDataService;
import com.huike.common.core.domain.entity.SysDictData;
import com.huike.common.utils.DictUtils;

/**
 * 字典 业务层处理
 * 
 * 
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataPlusMapper,SysDictData> implements ISysDictDataService {
	@Autowired
	private SysDictDataMapper dictDataMapper;

	/**
	 * 根据条件分页查询字典数据
	 * 
	 * @param dictData 字典数据信息
	 * @return 字典数据集合信息
	 * select dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark 
		from sys_dict_data
	 * <where>
		    <if test="dictType != null and dictType != ''">
				AND dict_type = #{dictType}
			</if>
			<if test="dictLabel != null and dictLabel != ''">
				AND dict_label like concat('%', #{dictLabel}, '%')
			</if>
			<if test="status != null and status != ''">
				AND status = #{status}
			</if>
		</where>
		order by dict_sort asc
	 */
	@Override
	public List<SysDictData> selectDictDataList(SysDictData dictData) {
		return this.list(new LambdaQueryWrapper<SysDictData>().
				eq(StringUtils.isNotEmpty(dictData.getDictType()),SysDictData::getDictType, dictData.getDictType()).
				eq(StringUtils.isNotEmpty(dictData.getStatus()),SysDictData::getStatus, dictData.getStatus()).
				like(StringUtils.isNotEmpty(dictData.getDictLabel()),SysDictData::getDictLabel, dictData.getDictLabel()).
				orderByAsc(SysDictData::getDictSort));
		
//		return dictDataMapper.selectDictDataList(dictData);
	}

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 * 
	 * @param dictType  字典类型
	 * @param dictValue 字典键值
	 * @return 字典标签
	 * select dict_label from sys_dict_data where dict_type = #{dictType} and dict_value = #{dictValue}
	 */
	@Override
	public String selectDictLabel(String dictType, String dictValue) {
		LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<SysDictData>();
		queryWrapper.eq(StringUtils.isNotEmpty(dictType),SysDictData::getDictType,dictType);
		queryWrapper.eq(StringUtils.isNotEmpty(dictValue),SysDictData::getDictValue,dictValue);
		SysDictData sysDictData = this.getOne(queryWrapper);
		if(ObjectUtils.isNotEmpty(sysDictData)) {
			return sysDictData.getDictLabel();
		}else{
			return null;
		}
	}

	/**
	 * 根据字典数据ID查询信息
	 * 
	 * @param dictCode 字典数据ID
	 * @return 字典数据
	 */
	@Override
	public SysDictData selectDictDataById(Long dictCode) {
		LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<SysDictData>();
		queryWrapper.eq(SysDictData::getDictCode, dictCode);
		return this.getOne(queryWrapper);
//		return dictDataMapper.selectDictDataById(dictCode);
	}

	/**
	 * 批量删除字典数据信息
	 * 
	 * @param dictCodes 需要删除的字典数据ID
	 * @return 结果
	 */
	@Override
	public int deleteDictDataByIds(Long[] dictCodes) {
		LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<SysDictData>();
		queryWrapper.in(SysDictData::getDictCode, dictCodes);
		boolean remove = this.remove(queryWrapper);
		if(remove) {
			DictUtils.clearDictCache();
		}
		return dictCodes.length;
//		int row = dictDataMapper.deleteDictDataByIds(dictCodes);
//		if (row > 0) {
//			DictUtils.clearDictCache();
//		}
//		return row;
	}

	/**
	 * 新增保存字典数据信息
	 * 
	 * @param dictData 字典数据信息
	 * @return 结果
	 */
	@Override
	public int insertDictData(SysDictData dictData) {
		int row = this.insertDictData(dictData);
//		int row = dictDataMapper.insertDictData(dictData);
		if (row > 0) {
			DictUtils.clearDictCache();
		}
		return row;
	}

	/**
	 * 修改保存字典数据信息
	 * 
	 * update sys_dict_data
 		<set>
 			<if test="dictSort != null">dict_sort = #{dictSort},</if>
 			<if test="dictLabel != null and dictLabel != ''">dict_label = #{dictLabel},</if>
 			<if test="dictValue != null and dictValue != ''">dict_value = #{dictValue},</if>
 			<if test="dictType != null and dictType != ''">dict_type = #{dictType},</if>
 			<if test="cssClass != null">css_class = #{cssClass},</if>
 			<if test="listClass != null">list_class = #{listClass},</if>
 			<if test="isDefault != null and isDefault != ''">is_default = #{isDefault},</if>
 			<if test="status != null">status = #{status},</if>
 			<if test="remark != null">remark = #{remark},</if>
 			<if test="updateBy != null and updateBy != ''">update_by = #{updateBy},</if>
 			update_time = sysdate()
 		</set>
 		where dict_code = #{dictCode}
	 * @param dictData 字典数据信息
	 * @return 结果
	 */
	@Override
	public int updateDictData(SysDictData dictData) {
		boolean update = this.update(new LambdaUpdateWrapper<SysDictData>().
				eq(SysDictData::getDictCode, dictData.getDictCode()).
				set(StringUtils.isNotEmpty(dictData.getDictSort()+""),SysDictData::getDictSort, dictData.getDictSort()).
				set(StringUtils.isNotEmpty(dictData.getDictLabel()),SysDictData::getDictLabel, dictData.getDictLabel()).
				set(StringUtils.isNotEmpty(dictData.getDictValue()),SysDictData::getDictValue, dictData.getDictValue()).
				set(StringUtils.isNotEmpty(dictData.getDictType()),SysDictData::getDictType, dictData.getDictType()).
				set(StringUtils.isNotEmpty(dictData.getCssClass()),SysDictData::getCssClass, dictData.getCssClass()).
				set(StringUtils.isNotEmpty(dictData.getListClass()),SysDictData::getListClass, dictData.getListClass()).
				set(StringUtils.isNotEmpty(dictData.getIsDefault()),SysDictData::getIsDefault, dictData.getIsDefault()).
				set(StringUtils.isNotEmpty(dictData.getStatus()),SysDictData::getStatus, dictData.getStatus()).
				set(StringUtils.isNotEmpty(dictData.getRemark()),SysDictData::getRemark, dictData.getRemark()).
				set(StringUtils.isNotEmpty(dictData.getUpdateBy()),SysDictData::getUpdateBy, dictData.getUpdateBy())
				);
		if(update) {
			DictUtils.clearDictCache();
			return 1;
		}
		return 0;
//		int row = dictDataMapper.updateDictData(dictData);
//		if (row > 0) {
//			DictUtils.clearDictCache();
//		}
//		return row;
	}
}
