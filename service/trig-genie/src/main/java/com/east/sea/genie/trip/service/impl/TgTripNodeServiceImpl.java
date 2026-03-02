package com.east.sea.genie.trip.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.genie.trip.mapper.TgTripNodeMapper;
import com.east.sea.genie.trip.pojo.dto.TgTripNodeUpdateBatchDTO;
import com.east.sea.genie.trip.pojo.entity.TgTripNodeEntity;
import com.east.sea.genie.trip.pojo.vo.TgTripNodeVO;
import com.east.sea.genie.trip.service.TgTripNodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TgTripNodeServiceImpl extends ServiceImpl<TgTripNodeMapper, TgTripNodeEntity> implements TgTripNodeService {

    @Override
    public List<TgTripNodeVO> getTripNodes(Long id) {
        LambdaQueryWrapper<TgTripNodeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TgTripNodeEntity::getPlanId, id)
                .orderByAsc(TgTripNodeEntity::getDayIndex, TgTripNodeEntity::getSortOrder);
        List<TgTripNodeEntity> nodes = list(wrapper);
        return nodes.stream().map(node -> {
            TgTripNodeVO nodeVO = new TgTripNodeVO();
            BeanUtils.copyProperties(node, nodeVO);
            // TODO: 查询关联的地点名称 (destName)
            return nodeVO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNodes(TgTripNodeUpdateBatchDTO tgTripNodeUpdateBatchDTO) {
        // 1. 删除旧节点 (简单粗暴策略：全删全增，或者根据ID判断更新)
        // 为简化逻辑，这里采用全量替换策略：删除该行程下所有节点，重新插入
        // 实际生产中建议做 Diff 更新
        LambdaQueryWrapper<TgTripNodeEntity> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(TgTripNodeEntity::getPlanId, tgTripNodeUpdateBatchDTO.getTripPlanId());
        remove(deleteWrapper);
        // 2. 插入新节点
        if (CollUtil.isNotEmpty(tgTripNodeUpdateBatchDTO.getTripNodeDTOList())) {
            List<TgTripNodeEntity> newNodes = tgTripNodeUpdateBatchDTO.getTripNodeDTOList().stream().map(dto -> {
                TgTripNodeEntity entity = new TgTripNodeEntity();
                BeanUtils.copyProperties(dto, entity);
                entity.setPlanId(tgTripNodeUpdateBatchDTO.getTripPlanId()); // 确保关联正确
                entity.setId(null); // 重置ID，强制新增
                return entity;
            }).collect(Collectors.toList());
            saveBatch(newNodes);
        }
    }

}
