package com.huike.web.controller.business;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huike.business.domain.TbBusiness;
import com.huike.business.service.ITbBusinessService;
import com.huike.clues.domain.vo.AssignmentVo;
import com.huike.clues.service.ITbClueService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;
import com.huike.common.utils.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 商机Controller
 * 
 * @date 2021-04-25
 */
@RestController
@RequestMapping("/business")
<<<<<<< HEAD
public class TbBusinessController extends BaseController {

    @Autowired
    private ITbBusinessService tbBusinessService;
    
    @Autowired
    private ITbClueService tbClueService;

    /**
     * 查询商机列表
     */
    //@ApiOperation("查询商机列表")
    @PreAuthorize("@ss.hasPermi('business:business:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbBusiness tbBusiness,HttpServletRequest req){
    	
    	if((!StringUtils.isEmpty(req.getParameter("params[beginCreateTime]")))&&
        		(!StringUtils.isEmpty(req.getParameter("params[endCreateTime]")))) {
        	tbBusiness.setBeginCreateTime(req.getParameter("params[beginCreateTime]"));
        	tbBusiness.setEndCreateTime(req.getParameter("params[endCreateTime]"));
        }
        List<TbBusiness> list = tbBusinessService.selectTbBusinessList(tbBusiness);
        return getDataTablePage(list);
    }



    //@ApiOperation("查询公海池")
    @PreAuthorize("@ss.hasPermi('business:business:pool')")
    @GetMapping("/pool")
    public TableDataInfo pool(TbBusiness tbBusiness,HttpServletRequest req){
    	
        startPage();
        if((!StringUtils.isEmpty(req.getParameter("params[beginCreateTime]")))&&
        		(!StringUtils.isEmpty(req.getParameter("params[endCreateTime]")))) {
        	tbBusiness.setBeginCreateTime(req.getParameter("params[beginCreateTime]"));
        	tbBusiness.setEndCreateTime(req.getParameter("params[endCreateTime]"));
        }
        List<TbBusiness> list = tbBusinessService.selectTbBusinessPool(tbBusiness);
        return getDataTable(list);
    }


    /**
     * 获取商机详细信息
     */
    //@ApiOperation("获取商机详细信息")
    @PreAuthorize("@ss.hasPermi('business:business:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbBusinessService.selectTbBusinessById(id));
    }

    /**
     * 新增商机
     */
    //@ApiOperation("新增商机")
    @PreAuthorize("@ss.hasPermi('business:business:add')")
    @Log(title = "商机", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbBusiness tbBusiness)
    {
    	if(!tbClueService.checkCluePhoneExis(tbBusiness.getPhone())) return error("手机号已存在");
        return toAjax(tbBusinessService.insertTbBusiness(tbBusiness));
    }

    /**
     * 修改商机
     */
    //@ApiOperation("修改商机")
    @PreAuthorize("@ss.hasPermi('business:business:edit')")
    @Log(title = "商机", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbBusiness tbBusiness)
    {
        return toAjax(tbBusinessService.updateTbBusiness(tbBusiness));
    }

    /**
     * 删除商机
     */
    //@ApiOperation("删除商机")
    @PreAuthorize("@ss.hasPermi('business:business:remove')")
    @Log(title = "商机", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbBusinessService.deleteTbBusinessByIds(ids));
    }

    //@ApiOperation("商机分配")
    @PreAuthorize("@ss.hasPermi('business:business:assignment')")
    @Log(title = "批量分配", businessType = BusinessType.UPDATE)
    @PutMapping("/assignment")
    public AjaxResult assignment(@RequestBody AssignmentVo assignmentVo)
    {
        return AjaxResult.success(tbBusinessService.assign(assignmentVo.getIds(),assignmentVo.getUserId()));
    }

    //@ApiOperation("批量捞取")
    @PreAuthorize("@ss.hasPermi('business:business:gain')")
    @Log(title = "批量捞取", businessType = BusinessType.UPDATE)
    @PutMapping("/gain")
    public AjaxResult gain(@RequestBody AssignmentVo assignmentVo)
    {
        return AjaxResult.success(tbBusinessService.gain(assignmentVo.getIds(),assignmentVo.getUserId()));
    }


    //@ApiOperation("踢回公海")
    @PreAuthorize("@ss.hasPermi('business:business:back')")
    @Log(title = "踢回公海", businessType = BusinessType.UPDATE)
    @PutMapping("/back/{id}/{reason}")
    public AjaxResult back(@PathVariable("id") Long id, @PathVariable("reason")String reason)
    {
        return AjaxResult.success(tbBusinessService.backPool(id,reason));
    }

}
=======
@Api("商机")
public class TbBusinessController extends BaseController {
	
	@Autowired
	private ITbBusinessService tbBusinessService;

	@Autowired
	private ITbClueService tbClueService;

	/**
	 * 查询商机列表
	 */
	@ApiOperation("查询商机列表")
	@PreAuthorize("@ss.hasPermi('business:business:list')")
	@GetMapping("/list")
	public TableDataInfo list(TbBusiness tbBusiness, HttpServletRequest req) {

		if ((!StringUtils.isEmpty(req.getParameter("params[beginCreateTime]")))
				&& (!StringUtils.isEmpty(req.getParameter("params[endCreateTime]")))) {
			tbBusiness.setBeginCreateTime(req.getParameter("params[beginCreateTime]"));
			tbBusiness.setEndCreateTime(req.getParameter("params[endCreateTime]"));
		}
		List<TbBusiness> list = tbBusinessService.selectTbBusinessList(tbBusiness);
		return getDataTablePage(list);
	}

	@ApiOperation("查询公海池")
	@PreAuthorize("@ss.hasPermi('business:business:pool')")
	@GetMapping("/pool")
	public TableDataInfo pool(TbBusiness tbBusiness, HttpServletRequest req) {

		startPage();
		if ((!StringUtils.isEmpty(req.getParameter("params[beginCreateTime]")))
				&& (!StringUtils.isEmpty(req.getParameter("params[endCreateTime]")))) {
			tbBusiness.setBeginCreateTime(req.getParameter("params[beginCreateTime]"));
			tbBusiness.setEndCreateTime(req.getParameter("params[endCreateTime]"));
		}
		List<TbBusiness> list = tbBusinessService.selectTbBusinessPool(tbBusiness);
		return getDataTable(list);
	}

	/**
	 * 获取商机详细信息
	 */
	@ApiOperation("获取商机详细信息")
	@PreAuthorize("@ss.hasPermi('business:business:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(tbBusinessService.selectTbBusinessById(id));
	}

	/**
	 * 新增商机
	 */
	@ApiOperation("新增商机")
	@PreAuthorize("@ss.hasPermi('business:business:add')")
	@Log(title = "商机", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody TbBusiness tbBusiness) {
		if (!tbClueService.checkCluePhoneExis(tbBusiness.getPhone()))
			return error("手机号已存在");
		return toAjax(tbBusinessService.insertTbBusiness(tbBusiness));
	}

	/**
	 * 修改商机
	 */
	@ApiOperation("修改商机")
	@PreAuthorize("@ss.hasPermi('business:business:edit')")
	@Log(title = "商机", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody TbBusiness tbBusiness) {
		return toAjax(tbBusinessService.updateTbBusiness(tbBusiness));
	}

	/**
	 * 删除商机
	 */
	@ApiOperation("删除商机")
	@PreAuthorize("@ss.hasPermi('business:business:remove')")
	@Log(title = "商机", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(tbBusinessService.deleteTbBusinessByIds(ids));
	}

	@ApiOperation("商机分配")
	@PreAuthorize("@ss.hasPermi('business:business:assignment')")
	@Log(title = "批量分配", businessType = BusinessType.UPDATE)
	@PutMapping("/assignment")
	public AjaxResult assignment(@RequestBody AssignmentVo assignmentVo) {
		return AjaxResult.success(tbBusinessService.assign(assignmentVo.getIds(), assignmentVo.getUserId()));
	}

	@ApiOperation("批量捞取")
	@PreAuthorize("@ss.hasPermi('business:business:gain')")
	@Log(title = "批量捞取", businessType = BusinessType.UPDATE)
	@PutMapping("/gain")
	public AjaxResult gain(@RequestBody AssignmentVo assignmentVo) {
		return AjaxResult.success(tbBusinessService.gain(assignmentVo.getIds(), assignmentVo.getUserId()));
	}

	@ApiOperation("踢回公海")
	@PreAuthorize("@ss.hasPermi('business:business:back')")
	@Log(title = "踢回公海", businessType = BusinessType.UPDATE)
	@PutMapping("/back/{id}/{reason}")
	public AjaxResult back(@PathVariable("id") Long id, @PathVariable("reason") String reason) {
		return AjaxResult.success(tbBusinessService.backPool(id, reason));
	}

}
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
