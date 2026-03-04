package com.east.sea.genie.social.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.east.sea.common.ApiResponse;
import com.east.sea.genie.social.pojo.dto.TgInteractionDTO;
import com.east.sea.genie.social.pojo.dto.TgSocialFeedDTO;
import com.east.sea.genie.social.pojo.dto.TgTravelLogDTO;
import com.east.sea.genie.social.pojo.vo.TgTravelLogVO;
import com.east.sea.genie.social.service.TgInteractionService;
import com.east.sea.genie.social.service.TgTravelLogService;
import com.east.sea.pojo.vo.PageResult;
import com.east.sea.util.PageApiResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 互动社交管理
 *
 * @author TraeAI
 * @since 2026-03-03
 */
@RestController
@RequestMapping("/social/")
@Api(tags = "互动社交管理")
@Validated
public class TgSocialController {

    @Resource
    private TgTravelLogService travelLogService;
    @Resource
    private TgInteractionService interactionService;

    @ApiOperation("发布游记/动态")
    @PostMapping("publish")
    public ApiResponse<Boolean> publish(@RequestBody @Valid TgTravelLogDTO logDTO) {
        return ApiResponse.ok(travelLogService.publish(logDTO));
    }

    @ApiOperation("获取动态列表(Feed流)")
    @GetMapping("feed")
    public ApiResponse<PageResult<TgTravelLogVO>> feed(@RequestBody TgSocialFeedDTO socialFeedDTO) {
        Page<TgTravelLogVO> page = travelLogService.feed(socialFeedDTO);
        return PageApiResponseUtil.buildPageApiResponse(page);
    }

    @ApiOperation("互动操作(点赞/收藏/关注)")
    @PostMapping("action")
    public ApiResponse<Boolean> action(@RequestBody @Valid TgInteractionDTO interactionDTO) {
        return ApiResponse.ok(interactionService.action(interactionDTO));
    }
}
