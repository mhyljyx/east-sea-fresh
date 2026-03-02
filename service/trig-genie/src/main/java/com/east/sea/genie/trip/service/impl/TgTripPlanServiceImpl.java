package com.east.sea.genie.trip.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.annotation.EditType;
import com.east.sea.exception.BusinessFrameException;
import com.east.sea.genie.enums.TgBaseCode;
import com.east.sea.genie.trip.mapper.TgTripPlanMapper;
import com.east.sea.genie.trip.pojo.dto.TgTripPlanDTO;
import com.east.sea.genie.trip.pojo.entity.TgTripPlanEntity;
import com.east.sea.genie.trip.pojo.vo.TgTripPlanVO;
import com.east.sea.genie.trip.service.TgTripPlanService;
import com.east.sea.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TgTripPlanServiceImpl extends ServiceImpl<TgTripPlanMapper, TgTripPlanEntity> implements TgTripPlanService {

    @Override
    public TgTripPlanVO getTripPlan(Long id) {
        TgTripPlanEntity planEntity = getById(id);
        if (ObjectUtil.isNull(planEntity)) {
            throw new BusinessFrameException(
                    TgBaseCode.TG_TRIP_PLAN_NOT_EXIST.getCode(),
                    TgBaseCode.TG_TRIP_PLAN_NOT_EXIST.getMsg()
            );
        }
        TgTripPlanVO vo = new TgTripPlanVO();
        BeanUtils.copyProperties(planEntity, vo);
        return vo;
    }

    @Override
    public Long create(TgTripPlanDTO planDTO) {
        TgTripPlanEntity entity = new TgTripPlanEntity();
        CopyUtil.copyProperties(planDTO, entity, EditType.INSERT);
        // TODO: 从Context获取当前登录用户ID
        // entity.setUserId(SecurityUtils.getUserId());
        entity.setUserId(1L); // Mock
        save(entity);
        return entity.getId();
    }

    @Override
    public TgTripPlanVO generate(TgTripPlanDTO planDTO) {
        // TODO: 对接 AI 大模型生成行程
        // 这里仅返回一个空的行程结构供演示
        TgTripPlanVO vo = new TgTripPlanVO();
        CopyUtil.copyProperties(planDTO, vo, EditType.INSERT);
        vo.setId(System.currentTimeMillis());
        return vo;
    }

}
