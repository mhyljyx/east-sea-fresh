package com.east.sea.genie.strategy.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.east.sea.annotation.EditType;
import com.east.sea.common.ApiResponse;
import com.east.sea.genie.strategy.pojo.dto.TgStrategyDTO;
import com.east.sea.genie.strategy.pojo.dto.TgStrategyQueryDTO;
import com.east.sea.genie.strategy.pojo.entity.TgStrategyEntity;
import com.east.sea.genie.strategy.pojo.vo.TgStrategyVO;
import com.east.sea.genie.strategy.service.TgStrategyService;
import com.east.sea.pojo.vo.PageResult;
import com.east.sea.util.CopyUtil;
import com.east.sea.util.PageApiResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
        Page<TgStrategyEntity> page = new Page<>(queryDTO.getPageIndex(), queryDTO.getPageSize());
        LambdaQueryWrapper<TgStrategyEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(queryDTO.getKeyword())) {
            wrapper.like(TgStrategyEntity::getTitle, queryDTO.getKeyword())
                   .or()
                   .like(TgStrategyEntity::getContent, queryDTO.getKeyword());
        }
        wrapper.eq(TgStrategyEntity::getStatus, 1) // 已发布
               .orderByDesc(TgStrategyEntity::getViewCount); // 默认按热度排序
        IPage<TgStrategyEntity> entityPage = tgStrategyService.page(page, wrapper);
        // 转换分页对象中的记录
        Page<TgStrategyVO> voPage = new Page<>();
        CopyUtil.copyProperties(entityPage, voPage);
        List<TgStrategyVO> voList = entityPage.getRecords().stream().map(entity -> {
            TgStrategyVO vo = new TgStrategyVO();
            CopyUtil.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
        voPage.setRecords(voList);
        return PageApiResponseUtil.buildPageApiResponse(voPage);
    }

    @ApiOperation("获取攻略详情")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('strategy:detail')")
    public ApiResponse<TgStrategyVO> getStrategy(@PathVariable
                                                     @NotNull(message = "攻略ID不能为空")
                                                     @Min(value = 1, message = "攻略ID必须大于0")
                                                     Long id) {
        TgStrategyEntity strategy = tgStrategyService.getById(id);
        if (ObjectUtil.isNotNull(strategy)) {
            // 增加浏览量（简单实现，实际建议用Redis）
            strategy.setViewCount(strategy.getViewCount() + 1);
            tgStrategyService.updateById(strategy);
            TgStrategyVO vo = new TgStrategyVO();
            CopyUtil.copyProperties(strategy, vo);
            return ApiResponse.ok(vo);
        }
        return ApiResponse.ok();
    }
    
    @ApiOperation("发布攻略")
    @PostMapping("publish")
    @PreAuthorize("hasAuthority('strategy:publish')")
    public ApiResponse<Boolean> publish(@RequestBody @Valid TgStrategyDTO strategy, HttpServletRequest request) {
        // TODO: 从request获取当前登录用户ID
        //strategy.setUserId(SecurityUtils.getUserId());
        TgStrategyEntity tgStrategyEntity = new TgStrategyEntity();
        CopyUtil.copyProperties(strategy, tgStrategyEntity, EditType.INSERT, EditType.UPDATE);
        return ApiResponse.ok(tgStrategyService.saveOrUpdate(tgStrategyEntity));
    }

}
