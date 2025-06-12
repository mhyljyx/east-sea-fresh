package com.east.sea.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.east.sea.common.ApiResponse;
import com.east.sea.pojo.params.OauthClientDetailsQueryParams;
import com.east.sea.pojo.view.OauthClientDetailsView;
import com.east.sea.service.OauthClientDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/oauth-client/api/")
public class OauthClientController {

    @Resource
    private OauthClientDetailsService oauthClientDetailsService;

    @PostMapping("query")
    public ApiResponse<Void> query(OauthClientDetailsQueryParams params) {
        IPage<OauthClientDetailsView> detailsViewIPage = oauthClientDetailsService.query(params);
        detailsViewIPage.get
    }

    @PostMapping
    public String add(@RequestBody Map<String, Object> client) throws SQLException {
    }

    @PutMapping("/{clientId}")
    public String update(@PathVariable String clientId, @RequestBody Map<String, Object> client) throws SQLException {
        return "success";
    }

    @DeleteMapping("/{clientId}")
    public String delete(@PathVariable String clientId) throws SQLException {
    }

    @GetMapping("/{clientId}")
    public Map<String, Object> get(@PathVariable String clientId) throws SQLException {
        return null;
    }
}
