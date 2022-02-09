package com.huike.clues.utils.easyExcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.huike.clues.domain.vo.TbClueExcelVo;
import com.huike.clues.service.ITbClueService;

public class ExcelListener extends AnalysisEventListener<TbClueExcelVo> {
	
	private Map<String,Integer> map = new ConcurrentHashMap<String,Integer>();
	
	//每过5条插入一次
	private static final int BATCH_COUNT = 10;
	
	private Integer rowCount=0;
	
	public ITbClueService clueService;
	
	public ExcelListener(ITbClueService clueService) {
		this.clueService = clueService;
    }
	

//	@Override
//	public void invoke(TbClueExcelVo data, AnalysisContext context) {
//		list.add(data);
//		rowCount++;
//        //得到当前操作表格的所有行数，由于行数包含了表头，所以需要减一为所有数据的条目数
//        Integer a =context.getTotalCount()-1;
//        //读取的行数到行尾时，将剩下的数据全部插入到数据库中
//        if (a==rowCount){
//	        System.out.println(list);
//        	Map<String, Integer> addTbClue = clueService.addTbClue(list);
//        	addMapData(addTbClue);
//        }
//        //每当list中存储的条目数达到2000条时，批量插入到数据库中，并清空当前list的数据
//        if (list.size()%BATCH_COUNT==0){
//        	Map<String, Integer> addTbClue = clueService.addTbClue(list);
//        	addMapData(addTbClue);
//            list.clear();
//        }
//	}
	
	@Override
	public void invoke(TbClueExcelVo data, AnalysisContext context) {
		List<TbClueExcelVo> list = new ArrayList<TbClueExcelVo>();
		list.add(data);
    	Map<String, Integer> addTbClue = clueService.addTbClue(list);
    	addMapData(addTbClue);
	}
	
	private void addMapData(Map<String, Integer> addTbClue) {
		Set<String> keySet = addTbClue.keySet();
		for (String indexKey : keySet) {
			Integer totalValue = map.get(indexKey);
			if(totalValue == null) {
				totalValue = 0;
			}
			Integer thisValue = addTbClue.get(indexKey);
			if(thisValue == null) {
				thisValue = 0;
			}
			map.put(indexKey, totalValue+thisValue);
		}
	}
	
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
	}
	
	public Map<String,Integer> getResultData(){
		return map;
	}
}