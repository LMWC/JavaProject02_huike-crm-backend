package com.huike.web.controller.report;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.TbClue;
import com.huike.clues.mapper.SysDeptMapper;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.contract.domain.TbContract;
import com.huike.report.domain.vo.ActivityStatisticsVo;
import com.huike.report.domain.vo.LineChartVo;
import com.huike.report.domain.vo.VulnerabilityMapVo;
import com.huike.report.service.IReportService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/report")
public class ReportController extends BaseController {

<<<<<<< HEAD
    @Autowired
   private IReportService reportService;

    //@ApiOperation("客户统计")
    @GetMapping("/contractStatistics/{beginCreateTime}/{endCreateTime}")
    public LineChartVo contractStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        return reportService.contractStatistics(beginCreateTime,endCreateTime);
    }

    //@ApiOperation("销售统计")
    @GetMapping("/salesStatistics/{beginCreateTime}/{endCreateTime}")
    public LineChartVo salesStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
      return reportService.salesStatistics(beginCreateTime,endCreateTime);
    }

    //@ApiOperation("学科分布统计")
    @GetMapping("/subjectStatistics/{beginCreateTime}/{endCreateTime}")
    public List<Map<String, Object>> subjectStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        return reportService.subjectStatistics(beginCreateTime,endCreateTime);
    }

    //@ApiOperation("线索统计")
    @GetMapping("/cluesStatistics/{beginCreateTime}/{endCreateTime}")
    public LineChartVo cluesStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        return reportService.cluesStatistics(beginCreateTime,endCreateTime);
    }

    //@ApiOperation("渠道统计")
    @GetMapping("/chanelStatistics/{beginCreateTime}/{endCreateTime}")
    public List<Map<String, Object>>  chanelStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        return reportService.chanelStatistics(beginCreateTime,endCreateTime);
    }


    //@ApiOperation("活动统计")
    @GetMapping("/activityStatistics/{beginCreateTime}/{endCreateTime}")
    public List<Map<String, Object>>  activityStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        return reportService.activityStatistics(beginCreateTime,endCreateTime);
    }

    //统计列表
    /**
     * 查询活动管理列表
     */
    //@ApiOperation("客户统计报表")
    @GetMapping("/contractStatisticsList")
    public TableDataInfo contractStatisticsList(TbContract contract)
    {
        startPage();
        List<TbContract> list= reportService.contractReportList(contract);
        return getDataTable(list);
    }


    //@ApiOperation("销售统计部门报表")
    @GetMapping("/deptStatisticsList/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo deptStatisticsList(@PathVariable String beginCreateTime, @PathVariable String endCreateTime)
    {
        startPage();
        List<Map<String, Object>> list= reportService.deptStatisticsList(beginCreateTime,endCreateTime);
        return getDataTablePage(list);
    }

    //@ApiOperation("销售统计渠道报表")
    @GetMapping("/channelStatisticsList/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo channelStatisticsList(@PathVariable String beginCreateTime, @PathVariable String endCreateTime)
    {
        startPage();
        List<Map<String, Object>> list= reportService.channelStatisticsList(beginCreateTime,endCreateTime);
        return getDataTablePage(list);
    }

    //@ApiOperation("销售统计归属人报表")
    @GetMapping("/ownerShipStatisticsList/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo ownerShipStatisticsList(@PathVariable String beginCreateTime, @PathVariable String endCreateTime)
    {
        startPage();
        List<Map<String, Object>> list = reportService.ownerShipStatisticsList(beginCreateTime,endCreateTime);
        return getDataTablePage(list);
    }


    //@ApiOperation("线索统计报表")
    @GetMapping("/cluesStatisticsList")
    public TableDataInfo cluesStatisticsList(TbClue clue)
    {
        startPage();
        List<TbClue> list= reportService.cluesStatisticsList(clue);
        return getDataTable(list);
    }

    //@ApiOperation("漏斗图数据")
    @GetMapping("/getVulnerabilityMap/{beginCreateTime}/{endCreateTime}")
    public AjaxResult getVulnerabilityMap(@PathVariable String beginCreateTime, @PathVariable String endCreateTime)
    {
        VulnerabilityMapVo vulnerabilityMapDTO= reportService.getVulnerabilityMap(beginCreateTime,endCreateTime);
        return AjaxResult.success(vulnerabilityMapDTO);
    }

    //@ApiOperation("活动渠道统计")
    @GetMapping("/activityStatisticsList")
    public TableDataInfo activityStatisticsList(TbActivity activity) {
        List<ActivityStatisticsVo> list= reportService.activityStatisticsList(activity);
        return getDataTablePage(list);
    }
=======
	@Autowired
	private IReportService reportService;

	@Autowired
	private SysDeptMapper sysDeptMapper;

	@ApiOperation("客户统计")
	@GetMapping("/contractStatistics/{beginCreateTime}/{endCreateTime}")
	public LineChartVo contractStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime) {
		return reportService.contractStatistics(beginCreateTime, endCreateTime);
	}

	@ApiOperation("销售统计")
	@GetMapping("/salesStatistics/{beginCreateTime}/{endCreateTime}")
	public LineChartVo salesStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime) {
		return reportService.salesStatistics(beginCreateTime, endCreateTime);
	}

	@ApiOperation("学科分布统计")
	@GetMapping("/subjectStatistics/{beginCreateTime}/{endCreateTime}")
	public List<Map<String, Object>> subjectStatistics(@PathVariable String beginCreateTime,
			@PathVariable String endCreateTime) {
		return reportService.subjectStatistics(beginCreateTime, endCreateTime);
	}

	@ApiOperation("线索统计")
	@GetMapping("/cluesStatistics/{beginCreateTime}/{endCreateTime}")
	public LineChartVo cluesStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime) {
		return reportService.cluesStatistics(beginCreateTime, endCreateTime);
	}

	@ApiOperation("渠道统计")
	@GetMapping("/chanelStatistics/{beginCreateTime}/{endCreateTime}")
	public List<Map<String, Object>> chanelStatistics(@PathVariable String beginCreateTime,
			@PathVariable String endCreateTime) {
		return reportService.chanelStatistics(beginCreateTime, endCreateTime);
	}

	@ApiOperation("活动统计")
	@GetMapping("/activityStatistics/{beginCreateTime}/{endCreateTime}")
	public List<Map<String, Object>> activityStatistics(@PathVariable String beginCreateTime,
			@PathVariable String endCreateTime) {
		return reportService.activityStatistics(beginCreateTime, endCreateTime);
	}

	// 统计列表
	/**
	 * 查询活动管理列表
	 */
	@ApiOperation("客户统计报表")
	@GetMapping("/contractStatisticsList")
	public TableDataInfo contractStatisticsList(TbContract contract) {
		startPage();
		List<TbContract> list = reportService.contractReportList(contract);
		return getDataTable(list);
	}

	@ApiOperation("销售统计部门报表")
	@GetMapping("/deptStatisticsList/{beginCreateTime}/{endCreateTime}")
	public TableDataInfo deptStatisticsList(@PathVariable String beginCreateTime, @PathVariable String endCreateTime) {
		startPage();
		List<Map<String, Object>> list = reportService.deptStatisticsList(beginCreateTime, endCreateTime);
		return getDataTablePage(list);
	}

	@ApiOperation("销售统计渠道报表")
	@GetMapping("/channelStatisticsList/{beginCreateTime}/{endCreateTime}")
	public TableDataInfo channelStatisticsList(@PathVariable String beginCreateTime,
			@PathVariable String endCreateTime) {
		startPage();
		List<Map<String, Object>> list = reportService.channelStatisticsList(beginCreateTime, endCreateTime);
		return getDataTablePage(list);
	}

	@ApiOperation("销售统计归属人报表")
	@GetMapping("/ownerShipStatisticsList/{beginCreateTime}/{endCreateTime}")
	public TableDataInfo ownerShipStatisticsList(@PathVariable String beginCreateTime,
			@PathVariable String endCreateTime) {
		startPage();
		List<Map<String, Object>> list = reportService.ownerShipStatisticsList(beginCreateTime, endCreateTime);
		return getDataTablePage(list);
	}

	@ApiOperation("线索统计报表")
	@GetMapping("/cluesStatisticsList")
	public TableDataInfo cluesStatisticsList(TbClue clue) {
		Integer deptId = clue.getDeptId();
		if (deptId != null) {
			// 查询出所有的父节点列表
			List<Integer> depeList = sysDeptMapper.selectAllChilDeptList(deptId);
			clue.setDeptIds(depeList);
		}
		startPage();
		List<TbClue> list = reportService.cluesStatisticsList(clue);
		return getDataTable(list);
	}

	@ApiOperation("漏斗图数据")
	@GetMapping("/getVulnerabilityMap/{beginCreateTime}/{endCreateTime}")
	public AjaxResult getVulnerabilityMap(@PathVariable String beginCreateTime, @PathVariable String endCreateTime) {
		VulnerabilityMapVo vulnerabilityMapDTO = reportService.getVulnerabilityMap(beginCreateTime, endCreateTime);
		return AjaxResult.success(vulnerabilityMapDTO);
	}

	@ApiOperation("活动渠道统计")
	@GetMapping("/activityStatisticsList")
	public TableDataInfo activityStatisticsList(TbActivity activity) {
		List<ActivityStatisticsVo> list = reportService.activityStatisticsList(activity);
		return getDataTablePage(list);
	}
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

}
