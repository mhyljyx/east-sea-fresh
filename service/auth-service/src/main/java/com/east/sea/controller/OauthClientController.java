package com.east.sea.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.east.sea.common.ApiResponse;
import com.east.sea.pojo.params.OauthClientDetailsDelParams;
import com.east.sea.pojo.params.OauthClientDetailsParams;
import com.east.sea.pojo.params.OauthClientDetailsQueryParams;
import com.east.sea.pojo.struct.PageResult;
import com.east.sea.pojo.view.OauthClientDetailsView;
import com.east.sea.service.OauthClientDetailsService;
import com.east.sea.util.PageApiResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/oauth-client/api/")
public class OauthClientController {

    @Resource
    private OauthClientDetailsService oauthClientDetailsService;

    @PostMapping("query")
    public ApiResponse<PageResult<OauthClientDetailsView>> query(OauthClientDetailsQueryParams params) {
        IPage<OauthClientDetailsView> detailsViewIPage = oauthClientDetailsService.query(params);
        return PageApiResponseUtil.buildPageApiResponse(detailsViewIPage);
    }

    @PostMapping("add")
    public ApiResponse<Void> add(@RequestBody @Valid OauthClientDetailsParams params) {
        oauthClientDetailsService.add(params);
        return ApiResponse.ok();
    }

    @PostMapping("update")
    public ApiResponse<Void> update(@RequestBody @Valid OauthClientDetailsParams params) {
        oauthClientDetailsService.update(params);
        return ApiResponse.ok();
    }

    @DeleteMapping("del")
    public ApiResponse<Void> delete(@RequestBody @Valid OauthClientDetailsDelParams params) {
        oauthClientDetailsService.del(params);
        return ApiResponse.ok();
    }

    @PostMapping("getInfo")
    public ApiResponse<OauthClientDetailsView> getInfo(@RequestParam(value = "id", required = false) @NotBlank(message = "Oauth客户端信息ID[id]不能为空") String id) {
        return ApiResponse.ok(oauthClientDetailsService.getInfo(id));
    }

}
