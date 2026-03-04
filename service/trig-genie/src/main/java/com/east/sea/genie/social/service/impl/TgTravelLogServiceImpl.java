package com.east.sea.genie.social.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.genie.social.mapper.TgInteractionMapper;
import com.east.sea.genie.social.mapper.TgTravelLogMapper;
import com.east.sea.genie.social.pojo.dto.TgSocialFeedDTO;
import com.east.sea.genie.social.pojo.dto.TgTravelLogDTO;
import com.east.sea.genie.social.pojo.entity.TgInteractionEntity;
import com.east.sea.genie.social.pojo.entity.TgTravelLogEntity;
import com.east.sea.genie.social.pojo.vo.TgTravelLogVO;
import com.east.sea.genie.social.service.TgTravelLogService;
import com.east.sea.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TgTravelLogServiceImpl extends ServiceImpl<TgTravelLogMapper, TgTravelLogEntity> implements TgTravelLogService {

    @Resource
    private TgInteractionMapper interactionMapper;

    @Override
    public Boolean publish(TgTravelLogDTO logDTO) {
        TgTravelLogEntity entity = new TgTravelLogEntity();
        CopyUtil.copyProperties(logDTO, entity);
        // JSON List 转 String
        if (CollUtil.isNotEmpty(logDTO.getMediaUrls())) {
            entity.setMediaUrls(JSONUtil.toJsonStr(logDTO.getMediaUrls()));
        }
        // TODO: 从Context获取当前登录用户ID
        // entity.setUserId(SecurityUtils.getUserId());
        entity.setUserId(1L); // Mock
        return save(entity);
    }

    @Override
    public Page<TgTravelLogVO> feed(TgSocialFeedDTO tgSocialFeedDTO) {
        Page<TgTravelLogEntity> page = new Page<>(tgSocialFeedDTO.getPageIndex(), tgSocialFeedDTO.getPageSize());
        LambdaQueryWrapper<TgTravelLogEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotNull(tgSocialFeedDTO.getUserId()), TgTravelLogEntity::getUserId, tgSocialFeedDTO.getUserId());
        wrapper.orderByDesc(TgTravelLogEntity::getCreateTime);
        
        Page<TgTravelLogEntity> entityPage = page(page, wrapper);
        
        Page<TgTravelLogVO> voPage = new Page<>();
        CopyUtil.copyProperties(entityPage, voPage);
        
        List<TgTravelLogVO> voList = entityPage.getRecords().stream().map(entity -> {
            TgTravelLogVO vo = new TgTravelLogVO();
            CopyUtil.copyProperties(entity, vo);
            // String 转 List
            if (StrUtil.isNotBlank(entity.getMediaUrls())) {
                vo.setMediaUrls(JSONUtil.toList(entity.getMediaUrls(), String.class));
            }
            // TODO: 填充用户信息 (userName, userAvatar) -> 需调用 UserService
            vo.setUserName("Mock用户");
            
            // 统计点赞数
            LambdaQueryWrapper<TgInteractionEntity> countWrapper = new LambdaQueryWrapper<>();
            countWrapper.eq(TgInteractionEntity::getTargetId, entity.getId())
                        .eq(TgInteractionEntity::getTargetType, 2) // 游记
                        .eq(TgInteractionEntity::getAction, 1); // 点赞
            vo.setLikeCount(interactionMapper.selectCount(countWrapper).intValue());
            
            // TODO: 判断当前用户是否已点赞
            vo.setIsLiked(false);
            
            return vo;
        }).collect(Collectors.toList());
        
        voPage.setRecords(voList);
        return voPage;
    }
}
