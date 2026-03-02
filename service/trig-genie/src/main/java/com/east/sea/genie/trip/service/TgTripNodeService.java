package com.east.sea.genie.trip.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.east.sea.genie.trip.pojo.dto.TgTripNodeUpdateBatchDTO;
import com.east.sea.genie.trip.pojo.entity.TgTripNodeEntity;
import com.east.sea.genie.trip.pojo.vo.TgTripNodeVO;

import java.util.List;

public interface TgTripNodeService extends IService<TgTripNodeEntity> {

    List<TgTripNodeVO> getTripNodes(Long id);

    void updateNodes(TgTripNodeUpdateBatchDTO tgTripNodeUpdateBatchDTO);

}
