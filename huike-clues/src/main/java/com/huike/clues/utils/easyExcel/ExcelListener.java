package com.huike.clues.utils.easyExcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.huike.clues.domain.dto.ImportResultDTO;
import com.huike.clues.domain.vo.TbClueExcelVo;
import com.huike.clues.service.ITbClueService;

/**
 * EasyExcel监听器，用于解析数据并执行操作
 */
public class ExcelListener extends AnalysisEventListener<TbClueExcelVo> {

	/**
	 * 利用构造方法获取对应的service
	 */
	public ITbClueService clueService;

	private ImportResultDTO resultDTO;

	/**
	 * 提供带参构造方法，在这里需要通过构造方法的方式获取对应的service层
	 * 谁调用这个监听器谁提供需要的service
	 * @param clueService
	 */
	public ExcelListener(ITbClueService clueService) {
		this.clueService = clueService;
		this.resultDTO = new ImportResultDTO();
	}

	/**
	 * 每解析一行数据都要执行一次
	 * 每条都执行一次插入操作
	 * @param data
	 * @param context
	 */
	@Override
	public void invoke(TbClueExcelVo data, AnalysisContext context) {
		ImportResultDTO addTbClue = clueService.importCluesData(data);
		resultDTO.addAll(addTbClue);
	}

	/**
	 * 当所有数据都解析完成后会执行
	 * @param context
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
	}

	/**
	 * 返回结果集对象
	 * @return
	 */
	public ImportResultDTO getResult(){
		return resultDTO;
	}

}