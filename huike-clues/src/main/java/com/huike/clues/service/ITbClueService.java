package com.huike.clues.service;

import java.util.List;
import java.util.Map;

import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.dto.ImportResultDTO;
import com.huike.clues.domain.vo.TbClueExcelVo;
import com.huike.common.core.domain.entity.SysUser;

/**
 * 线索管理Service接口
 * 
 * @author wgl
 * @date 2021-04-02
 */
public interface ITbClueService {
    /**
     * 查询线索管理
     * 
     * @param id 线索管理ID
     * @return 线索管理
     */
    public TbClue selectTbClueById(Long id);

    /**
     * 查询线索管理列表
     * 
     * @param tbClue 线索管理
     * @return 线索管理集合
     */
    public List<TbClue> selectTbClueList(TbClue tbClue);


    public List<TbClue> selectTbCluePool(TbClue tbClue);

    /**
     * 新增线索管理
     * 
     * @param tbClue 线索管理
     * @return 结果
     */
    public int insertTbClue(TbClue tbClue);

    /**
     * 修改线索管理
     * 
     * @param tbClue 线索管理
     * @return 结果
     */
    public int updateTbClue(TbClue tbClue);

    /**
     * 批量删除线索管理
     * 
     * @param ids 需要删除的线索管理ID
     * @return 结果
     */
    public int deleteTbClueByIds(Long[] ids);

    /**
     * 删除线索管理信息
     * 
     * @param id 线索管理ID
     * @return 结果
     */
    public int deleteTbClueById(Long id);

    /**
     * 导入线索数据
     * @param clueList
     * @return 结果
     */
    public Map<String,Integer> importClues(List<TbClue> clueList);


    public String assign(Long[] clueIds, Long userId);




    public String gain(Long[] clueIds, Long userId);




    /**
     * 修改下次跟进时间及线索状态
     * @param clueId
     * @param status
     * @return
     */
    public int updateStatus(Long clueId,String status);
    
    /**
     * 批量导入
     * @param list
     */
	public Map<String, Integer> addTbClue(List<TbClueExcelVo> list);
	
	/**
	 * 判断用户手机号是否存在
	 * @param phone
	 * @return
	 */
	public boolean checkCluePhoneExis(String phone);

    /**
     * 批量导入数据
     * @param data
     * @return
     */
    ImportResultDTO importCluesData(TbClueExcelVo data);
}
