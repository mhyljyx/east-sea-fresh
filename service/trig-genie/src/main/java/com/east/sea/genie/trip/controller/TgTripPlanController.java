package com.east.sea.genie.trip.controller;

import com.east.sea.common.ApiResponse;
import com.east.sea.genie.trip.pojo.dto.TgTripNodeUpdateBatchDTO;
import com.east.sea.genie.trip.pojo.dto.TgTripPlanDTO;
import com.east.sea.genie.trip.pojo.vo.TgTripNodeVO;
import com.east.sea.genie.trip.pojo.vo.TgTripPlanVO;
import com.east.sea.genie.trip.service.TgTripNodeService;
import com.east.sea.genie.trip.service.TgTripPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 行程规划管理
 *
 * @author TraeAI
 * @since 2026-02-28
 */
@Api(tags = "行程规划管理")
@Validated
@RestController
@RequestMapping("/trip/")
public class TgTripPlanController {

    @Resource
    private TgTripPlanService tgTripPlanService;
    @Resource
    private TgTripNodeService tgTripNodeService;

    @ApiOperation("创建行程")
    @PostMapping("create")
    public ApiResponse<Long> create(@RequestBody @Valid TgTripPlanDTO planDTO) {
        return ApiResponse.ok(tgTripPlanService.create(planDTO));
    }

    @ApiOperation("AI 生成行程建议 (Stub)")
    @PostMapping("generate")
    public ApiResponse<TgTripPlanVO> generate(@RequestBody @Valid TgTripPlanDTO planDTO) {
        return ApiResponse.ok(tgTripPlanService.generate(planDTO));
    }

    @ApiOperation("获取行程详情（含节点）")
    @GetMapping("{id}")
    public ApiResponse<TgTripPlanVO> getTripPlan(@PathVariable @NotNull(message = "行程id不能为空") Long id) {
        return ApiResponse.ok(tgTripPlanService.getTripPlan(id));
    }
    
    @ApiOperation("获取行程节点列表")
    @GetMapping("{id}/nodes")
    public ApiResponse<List<TgTripNodeVO>> getTripNodes(@PathVariable @NotNull(message = "行程id不能为空") Long id) {
        return ApiResponse.ok(tgTripNodeService.getTripNodes(id));
    }

    @ApiOperation("批量更新行程节点（排序/增删）")
    @PostMapping("updateNodes")
    public ApiResponse<Void> updateNodes(@RequestBody @Valid TgTripNodeUpdateBatchDTO tripNodeUpdateBatchDTO) {
        tgTripNodeService.updateNodes(tripNodeUpdateBatchDTO);
        return ApiResponse.ok();
    }
}
