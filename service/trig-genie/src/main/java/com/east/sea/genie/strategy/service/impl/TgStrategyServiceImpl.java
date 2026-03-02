package com.east.sea.genie.strategy.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.annotation.EditType;
import com.east.sea.genie.strategy.mapper.TgStrategyMapper;
import com.east.sea.genie.strategy.pojo.dto.TgStrategyDTO;
import com.east.sea.genie.strategy.pojo.dto.TgStrategyQueryDTO;
import com.east.sea.genie.strategy.pojo.entity.TgStrategyEntity;
import com.east.sea.genie.strategy.pojo.vo.TgStrategyVO;
import com.east.sea.genie.strategy.service.TgStrategyService;
import com.east.sea.pojo.vo.PageResult;
import com.east.sea.util.CopyUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TgStrategyServiceImpl extends ServiceImpl<TgStrategyMapper, TgStrategyEntity> implements TgStrategyService {

    @Override
    public Page<TgStrategyVO> query(TgStrategyQueryDTO queryDTO) {
        Page<TgStrategyEntity> page = new Page<>(queryDTO.getPageIndex(), queryDTO.getPageSize());
        LambdaQueryWrapper<TgStrategyEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(queryDTO.getKeyword())) {
            wrapper.like(TgStrategyEntity::getTitle, queryDTO.getKeyword())
                    .or()
                    .like(TgStrategyEntity::getContent, queryDTO.getKeyword());
        }
        wrapper.eq(TgStrategyEntity::getStatus, 1) // 已发布
                .orderByDesc(TgStrategyEntity::getViewCount); // 默认按热度排序
        IPage<TgStrategyEntity> entityPage = page(page, wrapper);
        // 转换分页对象中的记录
        Page<TgStrategyVO> voPage = new Page<>();
        CopyUtil.copyProperties(entityPage, voPage);
        List<TgStrategyVO> voList = entityPage.getRecords().stream().map(entity -> {
            TgStrategyVO vo = new TgStrategyVO();
            CopyUtil.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
        voPage.setRecords(voList);
        voPage.setCurrent(entityPage.getCurrent());
        voPage.setPages(entityPage.getPages());
        voPage.setTotal(entityPage.getTotal());
        voPage.setSize(entityPage.getSize());
        return voPage;
    }

    @Override
    public TgStrategyVO getStrategy(Long id) {
        TgStrategyEntity strategy = getById(id);
        if (ObjectUtil.isNotNull(strategy)) {
            // 增加浏览量（简单实现，实际建议用Redis）
            strategy.setViewCount(strategy.getViewCount() + 1);
            updateById(strategy);
            TgStrategyVO vo = new TgStrategyVO();
            CopyUtil.copyProperties(strategy, vo);
            return vo;
        }
        return null;
    }

    @Override
    public Boolean publish(TgStrategyDTO strategy) {
        // TODO: 从Context获取当前登录用户ID
        //strategy.setUserId(SecurityUtils.getUserId());
        TgStrategyEntity tgStrategyEntity = new TgStrategyEntity();
        CopyUtil.copyProperties(strategy, tgStrategyEntity, EditType.INSERT, EditType.UPDATE);
        return saveOrUpdate(tgStrategyEntity);
    }
}
