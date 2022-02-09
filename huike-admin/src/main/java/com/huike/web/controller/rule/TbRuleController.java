package com.huike.web.controller.rule;

import java.util.List;
import java.util.stream.Collectors;

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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huike.clues.domain.TbRuleAssign;
import com.huike.clues.domain.vo.RuleOrderVo;
import com.huike.clues.dto.TbRuleAssignDto;
import com.huike.clues.service.ISysDeptService;
import com.huike.clues.service.ISysUserService;
import com.huike.clues.service.ITbRuleAssignService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysDept;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;
import com.huike.common.utils.StringUtils;

import io.swagger.annotations.ApiOperation;

/**
 * 规则分配Controller
 * 
 * @date 2021-04-08
 */
@RestController
@RequestMapping("/rule")
public class TbRuleController extends BaseController {
	
	@Autowired
	private ITbRuleAssignService tbRuleAssignService;

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private ISysDeptService sysDeptService;

	/**
	 * 查询规则分配列表
	 */
	@ApiOperation("规则分页列表")
	@PreAuthorize("@ss.hasPermi('rule:rule:list')")
	@GetMapping("/list/{type}")
	public TableDataInfo list(@PathVariable String type) {
		TbRuleAssignDto tbRuleAssign = new TbRuleAssignDto();
		tbRuleAssign.setType(type);
		Page<TbRuleAssign> list = tbRuleAssignService.selectTbRuleAssignPage(tbRuleAssign);
//		List<TbRuleAssign> list = tbRuleAssignService.selectTbRuleAssignList(tbRuleAssign);
		// 为前端封装赋值对象名称
		list.getRecords().forEach(d -> setAssignObjectName(d));
		return getDataTable(list);
	}

	private void setAssignObjectName(TbRuleAssign d) {
		String userType = d.getAssignType();
		String assignObject = d.getAssignObject();
		String[] assign_array = assignObject.split(",");
		for (String assign : assign_array) {
			if (StringUtils.isBlank(assign)) {
				continue;
			}
			Long id = Long.valueOf(assign);
			// 用户
			if (TbRuleAssign.AssignType.USER.getValue().equals(userType)) {
				SysUser sysUser = sysUserService.selectUserById(id);
				d.getAssignObjectNames().add(sysUser.getUserName());
			} else if (TbRuleAssign.AssignType.DEPARTMENT.getValue().equals(userType)) {
				// 岗部门
				SysDept sysDept = sysDeptService.selectDeptById(id);
				d.getAssignObjectNames().add(sysDept.getDeptName());
			}
		}

	}

	/**
	 * 获取规则分配详细信息
	 */
	@ApiOperation("查询规则")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		TbRuleAssign tbRuleAssign = tbRuleAssignService.selectTbRuleAssignById(id);
		setAssignObjectName(tbRuleAssign);
		return AjaxResult.success(tbRuleAssign);
	}

	/**
	 * 新增规则分配
	 */
	@ApiOperation("新增规则")
	@Log(title = "规则分配", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody TbRuleAssign ruleAssign) {
		return toAjax(tbRuleAssignService.insertTbRuleAssign(ruleAssign));
	}

	/**
	 * 修改规则分配
	 */
	@ApiOperation("修改规则")
	@Log(title = "规则分配", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody TbRuleAssign tbRuleAssign) {
		return toAjax(tbRuleAssignService.updateTbRuleAssign(tbRuleAssign));
	}

	/**
	 * 删除规则分配
	 */
	@ApiOperation("删除规则")
	@Log(title = "删除规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	public AjaxResult remove(@PathVariable Long id) {
		return toAjax(tbRuleAssignService.deleteTbRuleAssignById(id));
	}

	@ApiOperation("规则排序")
	@Log(title = "规则排序", businessType = BusinessType.UPDATE)
	@PutMapping("/order")
	public AjaxResult order(@RequestBody List<RuleOrderVo> ruleOrders) {
		List<TbRuleAssign> list = ruleOrders.stream().map(ruleOrderVo -> {
			TbRuleAssign ruleAssign = new TbRuleAssign();
			ruleAssign.setId(ruleOrderVo.getId());
			ruleAssign.setRuleOrder(ruleOrderVo.getRuleOrder());
			return ruleAssign;
		}).collect(Collectors.toList());

		tbRuleAssignService.updateOrders(list);
		return AjaxResult.success("sucess");
	}

}
