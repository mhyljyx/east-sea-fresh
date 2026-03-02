package com.east.sea.genie.trip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.genie.trip.mapper.TgTripPlanMapper;
import com.east.sea.genie.trip.pojo.entity.TgTripPlanEntity;
import com.east.sea.genie.trip.service.TgTripPlanService;
import org.springframework.stereotype.Service;

@Service
public class TgTripPlanServiceImpl extends ServiceImpl<TgTripPlanMapper, TgTripPlanEntity> implements TgTripPlanService {
}
