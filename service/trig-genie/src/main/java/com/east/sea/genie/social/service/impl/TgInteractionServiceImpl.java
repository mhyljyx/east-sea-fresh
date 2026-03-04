package com.east.sea.genie.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.genie.social.mapper.TgInteractionMapper;
import com.east.sea.genie.social.pojo.dto.TgInteractionDTO;
import com.east.sea.genie.social.pojo.entity.TgInteractionEntity;
import com.east.sea.genie.social.service.TgInteractionService;
import com.east.sea.util.CopyUtil;
import org.springframework.stereotype.Service;

@Service
public class TgInteractionServiceImpl extends ServiceImpl<TgInteractionMapper, TgInteractionEntity> implements TgInteractionService {

    @Override
    public Boolean action(TgInteractionDTO interactionDTO) {
        // 1. 检查是否已经操作过（防止重复点赞等）
        // TODO: 从Context获取当前登录用户ID
        Long userId = 1L; // Mock
        
        LambdaQueryWrapper<TgInteractionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TgInteractionEntity::getUserId, userId)
               .eq(TgInteractionEntity::getTargetId, interactionDTO.getTargetId())
               .eq(TgInteractionEntity::getTargetType, interactionDTO.getTargetType())
               .eq(TgInteractionEntity::getAction, interactionDTO.getAction());
               
        TgInteractionEntity exist = getOne(wrapper);
        if (exist != null) {
            // 如果存在，则是取消操作（取消点赞/收藏）
            return removeById(exist.getId());
        } else {
            // 如果不存在，则是执行操作
            TgInteractionEntity entity = new TgInteractionEntity();
            CopyUtil.copyProperties(interactionDTO, entity);
            entity.setUserId(userId);
            return save(entity);
        }
    }
}
