package com.east.sea.genie.strategy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.east.sea.genie.strategy.pojo.entity.TgDestinationEntity;
import com.east.sea.genie.strategy.pojo.vo.TgDestinationVO;

import java.util.List;

public interface TgDestinationService extends IService<TgDestinationEntity> {

    List<TgDestinationVO> list(Long destId);

    TgDestinationVO getDestination(Long id);
}
