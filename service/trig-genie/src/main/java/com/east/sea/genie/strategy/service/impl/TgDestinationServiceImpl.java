package com.east.sea.genie.strategy.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.exception.BusinessFrameException;
import com.east.sea.genie.enums.TgBaseCode;
import com.east.sea.genie.strategy.mapper.TgDestinationMapper;
import com.east.sea.genie.strategy.pojo.entity.TgDestinationEntity;
import com.east.sea.genie.strategy.pojo.vo.TgDestinationVO;
import com.east.sea.genie.strategy.service.TgDestinationService;
import com.east.sea.util.CopyUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TgDestinationServiceImpl extends ServiceImpl<TgDestinationMapper, TgDestinationEntity> implements TgDestinationService {

    @Override
    public List<TgDestinationVO> list(Long destId) {
        LambdaQueryWrapper<TgDestinationEntity> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(destId)) {
            wrapper.eq(TgDestinationEntity::getParentId, destId)
                    .or()
                    .eq(TgDestinationEntity::getId, destId);
        }
        List<TgDestinationEntity> list = list(wrapper);
        return list.stream().map(entity -> {
            TgDestinationVO vo = new TgDestinationVO();
            CopyUtil.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public TgDestinationVO getDestination(Long id) {
        TgDestinationEntity entity = getById(id);
        if (ObjectUtil.isNull(entity)) {
            throw new BusinessFrameException(
                    TgBaseCode.TG_DESTINATION_DETAIL_NOT_EXIST.getCode(),
                    TgBaseCode.TG_DESTINATION_DETAIL_NOT_EXIST.getMsg()
            );
        }
        TgDestinationVO vo = new TgDestinationVO();
        CopyUtil.copyProperties(entity, vo);
        return vo;
    }
}
