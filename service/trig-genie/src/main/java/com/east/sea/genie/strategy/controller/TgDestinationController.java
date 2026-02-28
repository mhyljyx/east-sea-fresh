package com.east.sea.genie.strategy.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.east.sea.common.ApiResponse;
import com.east.sea.genie.strategy.pojo.entity.TgDestinationEntity;
import com.east.sea.genie.strategy.pojo.entity.TgStrategyEntity;
import com.east.sea.genie.strategy.pojo.vo.TgDestinationVO;
import com.east.sea.genie.strategy.pojo.vo.TgStrategyVO;
import com.east.sea.genie.strategy.service.TgDestinationService;
import com.east.sea.genie.strategy.service.TgStrategyService;
import com.east.sea.pojo.vo.PageResult;
import com.east.sea.util.CopyUtil;
import com.east.sea.util.PageApiResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 目的地管理
 *
 * @author  tztang
 * @since 2026-02-27
 */
@Api(tags = "目的地管理")
@Validated
@RestController
@RequestMapping("/tg/destination/")
public class TgDestinationController {

    @Resource
    private TgDestinationService tgDestinationService;

    @GetMapping("list")
    @ApiOperation("获取目的地列表（树形或平铺）")
    @PreAuthorize("hasAuthority('destination:list')")
    public ApiResponse<List<TgDestinationVO>> list(@RequestParam(required = false) Long destId) {
        LambdaQueryWrapper<TgDestinationEntity> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(destId)) {
            wrapper.eq(TgDestinationEntity::getParentId, destId)
                    .or()
                    .eq(TgDestinationEntity::getId, destId);
        }
        List<TgDestinationEntity> list = tgDestinationService.list(wrapper);
        List<TgDestinationVO> voList = list.stream().map(entity -> {
            TgDestinationVO vo = new TgDestinationVO();
            CopyUtil.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
        return ApiResponse.ok(voList);
    }

    @GetMapping("{id}")
    @ApiOperation("获取目的地详情")
    @PreAuthorize("hasAuthority('destination:detail')")
    public ApiResponse<TgDestinationVO> getDestination(@PathVariable
                                                           @NotNull(message = "目的地ID不能为空")
                                                           @Min(value = 1, message = "目的地ID必须大于0")
                                                           Long id) {
        TgDestinationEntity entity = tgDestinationService.getById(id);
        if (ObjectUtil.isNull(entity)) {
            return ApiResponse.ok();
        }
        TgDestinationVO vo = new TgDestinationVO();
        CopyUtil.copyProperties(entity, vo);
        return ApiResponse.ok(vo);
    }

}
