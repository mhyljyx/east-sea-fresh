package com.east.sea.genie.strategy.controller;

import cn.hutool.core.util.ObjectUtil;
import com.east.sea.common.ApiResponse;
import com.east.sea.genie.strategy.pojo.dto.TgStrategyDTO;
import com.east.sea.genie.strategy.pojo.dto.TgStrategyQueryDTO;
import com.east.sea.genie.strategy.pojo.vo.TgStrategyVO;
import com.east.sea.genie.strategy.service.TgStrategyService;
import com.east.sea.pojo.vo.PageResult;
import com.east.sea.util.PageApiResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 攻略管理
 *
 * @author  tztang
 * @since 2026-02-27
 */
@Api(tags = "攻略管理")
@RestController
@RequestMapping("/tg/strategy/")
public class TgStrategyController {

    @Resource
    private TgStrategyService tgStrategyService;

    @ApiOperation("搜索攻略")
    @GetMapping("query")
    @PreAuthorize("hasAuthority('strategy:query')")
    public ApiResponse<PageResult<TgStrategyVO>> query(@RequestBody TgStrategyQueryDTO queryDTO) {
        return PageApiResponseUtil.buildPageApiResponse(tgStrategyService.query(queryDTO));
    }

    @ApiOperation("获取攻略详情")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('strategy:detail')")
    public ApiResponse<TgStrategyVO> getStrategy(@PathVariable
                                                     @NotNull(message = "攻略ID不能为空")
                                                     @Min(value = 1, message = "攻略ID必须大于0")
                                                     Long id) {
        TgStrategyVO vo = tgStrategyService.getStrategy(id);
        if (ObjectUtil.isNotNull(vo)) {
            return ApiResponse.ok(vo);
        }
        return ApiResponse.ok();
    }
    
    @ApiOperation("发布攻略")
    @PostMapping("publish")
    @PreAuthorize("hasAuthority('strategy:publish')")
    public ApiResponse<Boolean> publish(@RequestBody @Valid TgStrategyDTO strategy, HttpServletRequest request) {
        return ApiResponse.ok(tgStrategyService.publish(strategy));
    }

}
