package com.east.sea.service;

import com.east.sea.pojo.dto.sys.PermScanRequestDTO;
import com.east.sea.pojo.vo.sys.PermScanResultVO;

public interface SysPermScanService {

    PermScanResultVO scanAndMerge(PermScanRequestDTO request);
}

