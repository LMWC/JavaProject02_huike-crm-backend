package com.huike.web.controller.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;
import com.huike.contract.domain.vo.TransferVo;
import com.huike.contract.service.ITransferService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/transfer")
public class TransferController extends BaseController {

	@Autowired
	ITransferService transferService;

	/**
	 * 获取转派列表
	 */
	@ApiOperation("转派列表")
	@PreAuthorize("@ss.hasPermi('transfer:transfer:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysUser user) {
		List<TransferVo> list = transferService.selectTransferList(user);
		return getDataTablePage(list);
	}

//  @ApiOperation("转派处理")
	@PreAuthorize("@ss.hasPermi('transfer:transfer:assignment')")
	@Log(title = "转派处理", businessType = BusinessType.UPDATE)
	@PutMapping("/assignment/{type}/{userId}/{transferUserId}")
	public AjaxResult assignment(@PathVariable("type") String type, @PathVariable("userId") Long userId,
			@PathVariable("transferUserId") Long transferUserId) {
		return AjaxResult.success(transferService.assignment(type, userId, transferUserId));
	}

}
