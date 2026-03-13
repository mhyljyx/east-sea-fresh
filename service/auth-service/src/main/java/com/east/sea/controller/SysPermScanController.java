package com.east.sea.controller;

import com.east.sea.common.ApiResponse;
import com.east.sea.pojo.dto.sys.PermScanRequestDTO;
import com.east.sea.pojo.vo.sys.PermScanResultVO;
import com.east.sea.service.SysPermScanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "权限扫描")
@Validated
@RestController
@RequestMapping("/sys/perm/")
public class SysPermScanController {

    @Resource
    private SysPermScanService sysPermScanService;

    @ApiOperation("扫描控制器并合并菜单清单")
    @PostMapping("scan")
    public ApiResponse<PermScanResultVO> scan(@RequestBody PermScanRequestDTO request) {
        return ApiResponse.ok(sysPermScanService.scanAndMerge(request));
    }
}

