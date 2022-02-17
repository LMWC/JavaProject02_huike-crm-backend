package com.huike.web.controller.contract;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huike.clues.service.ITbClueService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;
import com.huike.contract.domain.TbContract;
import com.huike.contract.service.ITbContractService;

/**
 * 合同Controller
 */
@RestController
@RequestMapping("/contract")
public class TbContractController extends BaseController
{
    @Autowired
    private ITbContractService tbContractService;
    
    @Autowired
    private ITbClueService tbClueService;


    /**
     * 商机转合同
     * @param id
     * @param tbContract
     * @return
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:change')")
    @Log(title = "商机转合同", businessType = BusinessType.UPDATE)
    @PutMapping("/changeContract/{id}")
    public AjaxResult changeContract(@PathVariable Long id, @RequestBody TbContract tbContract){
        return toAjax(tbContractService.changeContract(id,tbContract));
    }

    /**
     * 查询合同列表
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbContract tbContract){
    	List<TbContract> contractList = tbContractService.selectTbContract(tbContract);
        return getDataTablePage(contractList);
    }



    /**
     * 获取合同详细信息
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:detail')")
    @GetMapping(value = "/detail/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbContractService.selectTbContractById(id));
    }
    
    @PreAuthorize("@ss.hasPermi('contract:contract:detail')")
    @GetMapping(value = "/{id}")
    public AjaxResult detailById(@PathVariable("id") Long id){
        return AjaxResult.success(tbContractService.selectTbContractById(id));
    }


    /**
     * 新增合同
     */
    //@ApiOperation("新增合同")
    @PreAuthorize("@ss.hasPermi('contract:contract:add')")
    @Log(title = "合同", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbContract tbContract){
    	if(!tbClueService.checkCluePhoneExis(tbContract.getPhone())) return error("手机号已存在");
        return toAjax(tbContractService.insertTbContract(tbContract));
    }



    /**
     * 修改合同
     */
    //@ApiOperation("修改合同")
    @PreAuthorize("@ss.hasPermi('contract:contract:edit')")
    @Log(title = "合同", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbContract tbContract)
    {
        return toAjax(tbContractService.updateTbContract(tbContract));
    }

}
