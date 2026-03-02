package com.east.sea.genie.strategy.controller;

import cn.hutool.core.util.ObjectUtil;
import com.east.sea.common.ApiResponse;
import com.east.sea.genie.strategy.pojo.vo.TgDestinationVO;
import com.east.sea.genie.strategy.service.TgDestinationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

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
        return ApiResponse.ok(tgDestinationService.list(destId));
    }

    @GetMapping("{id}")
    @ApiOperation("获取目的地详情")
    @PreAuthorize("hasAuthority('destination:detail')")
    public ApiResponse<TgDestinationVO> getDestination(@PathVariable
                                                           @NotNull(message = "目的地ID不能为空")
                                                           @Min(value = 1, message = "目的地ID必须大于0")
                                                           Long id) {
        TgDestinationVO vo = tgDestinationService.getDestination(id);
        if (ObjectUtil.isNull(vo)) {
            return ApiResponse.ok();
        }
        return ApiResponse.ok(vo);
    }

}
