package com.east.sea.genie.trip.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.east.sea.annotation.EditType;
import com.east.sea.common.ApiResponse;
import com.east.sea.genie.trip.pojo.dto.TgTripNodeUpdateBatchDTO;
import com.east.sea.genie.trip.pojo.dto.TgTripPlanDTO;
import com.east.sea.genie.trip.pojo.entity.TgTripNodeEntity;
import com.east.sea.genie.trip.pojo.entity.TgTripPlanEntity;
import com.east.sea.genie.trip.pojo.vo.TgTripNodeVO;
import com.east.sea.genie.trip.pojo.vo.TgTripPlanVO;
import com.east.sea.genie.trip.service.TgTripNodeService;
import com.east.sea.genie.trip.service.TgTripPlanService;
import com.east.sea.util.CopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 行程规划管理
 *
 * @author TraeAI
 * @since 2026-02-28
 */
@Api(tags = "行程规划管理")
@Validated
@RestController
@RequestMapping("/trip")
public class TgTripPlanController {

    @Resource
    private TgTripPlanService tgTripPlanService;
    @Resource
    private TgTripNodeService tgTripNodeService;

    @ApiOperation("创建行程")
    @PostMapping("/create")
    public ApiResponse<Long> create(@RequestBody @Valid TgTripPlanDTO planDTO) {
        TgTripPlanEntity entity = new TgTripPlanEntity();
        CopyUtil.copyProperties(planDTO, entity, EditType.INSERT);
        // TODO: 从Context获取当前登录用户ID
        // entity.setUserId(SecurityUtils.getUserId());
        entity.setUserId(1L); // Mock
        tgTripPlanService.save(entity);
        return ApiResponse.ok(entity.getId());
    }

    @ApiOperation("AI 生成行程建议 (Stub)")
    @PostMapping("/generate")
    public ApiResponse<TgTripPlanVO> generate(@RequestBody @Valid TgTripPlanDTO planDTO) {
        // TODO: 对接 AI 大模型生成行程
        // 这里仅返回一个空的行程结构供演示
        TgTripPlanVO vo = new TgTripPlanVO();
        CopyUtil.copyProperties(planDTO, vo, EditType.INSERT);
        vo.setId(System.currentTimeMillis());
        return ApiResponse.ok(vo);
    }

    @ApiOperation("获取行程详情（含节点）")
    @GetMapping("/{id}")
    public ApiResponse<TgTripPlanVO> getTripPlan(@PathVariable @NotNull(message = "行程id不能为空") Long id) {
        TgTripPlanEntity planEntity = tgTripPlanService.getById(id);
        if (planEntity == null) {
            return ApiResponse.ok();
        }
        TgTripPlanVO vo = new TgTripPlanVO();
        BeanUtils.copyProperties(planEntity, vo);
        return ApiResponse.ok(vo);
    }
    
    @ApiOperation("获取行程节点列表")
    @GetMapping("/{id}/nodes")
    public ApiResponse<List<TgTripNodeVO>> getTripNodes(@PathVariable @NotNull(message = "行程id不能为空") Long id) {
        LambdaQueryWrapper<TgTripNodeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TgTripNodeEntity::getPlanId, id)
               .orderByAsc(TgTripNodeEntity::getDayIndex, TgTripNodeEntity::getSortOrder);
        List<TgTripNodeEntity> nodes = tgTripNodeService.list(wrapper);
        List<TgTripNodeVO> voList = nodes.stream().map(node -> {
            TgTripNodeVO nodeVO = new TgTripNodeVO();
            BeanUtils.copyProperties(node, nodeVO);
            // TODO: 查询关联的地点名称 (destName)
            return nodeVO;
        }).collect(Collectors.toList());
        return ApiResponse.ok(voList);
    }

    @ApiOperation("批量更新行程节点（排序/增删）")
    @PostMapping("updateNodes")
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<Boolean> updateNodes(@RequestBody @Valid TgTripNodeUpdateBatchDTO tgTripNodeUpdateBatchDTO) {
        // 1. 删除旧节点 (简单粗暴策略：全删全增，或者根据ID判断更新)
        // 为简化逻辑，这里采用全量替换策略：删除该行程下所有节点，重新插入
        // 实际生产中建议做 Diff 更新
        LambdaQueryWrapper<TgTripNodeEntity> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(TgTripNodeEntity::getPlanId, tgTripNodeUpdateBatchDTO.getTripPlanId());
        tgTripNodeService.remove(deleteWrapper);
        // 2. 插入新节点
        if (CollUtil.isNotEmpty(tgTripNodeUpdateBatchDTO.getTripNodeDTOList())) {
            List<TgTripNodeEntity> newNodes = tgTripNodeUpdateBatchDTO.getTripNodeDTOList().stream().map(dto -> {
                TgTripNodeEntity entity = new TgTripNodeEntity();
                BeanUtils.copyProperties(dto, entity);
                entity.setPlanId(tgTripNodeUpdateBatchDTO.getTripPlanId()); // 确保关联正确
                entity.setId(null); // 重置ID，强制新增
                return entity;
            }).collect(Collectors.toList());
            tgTripNodeService.saveBatch(newNodes);
        }
        return ApiResponse.ok();
    }
}
