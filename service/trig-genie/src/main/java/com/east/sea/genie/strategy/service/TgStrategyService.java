package com.east.sea.genie.strategy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.east.sea.genie.strategy.pojo.dto.TgStrategyDTO;
import com.east.sea.genie.strategy.pojo.dto.TgStrategyQueryDTO;
import com.east.sea.genie.strategy.pojo.entity.TgStrategyEntity;
import com.east.sea.genie.strategy.pojo.vo.TgStrategyVO;

public interface TgStrategyService extends IService<TgStrategyEntity> {

    Page<TgStrategyVO> query(TgStrategyQueryDTO queryDTO);

    TgStrategyVO getStrategy(Long id);

    Boolean publish(TgStrategyDTO strategy);
}
