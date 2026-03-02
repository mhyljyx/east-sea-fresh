package com.east.sea.genie.trip.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.east.sea.common.ApiResponse;
import com.east.sea.genie.trip.pojo.dto.TgTripPlanDTO;
import com.east.sea.genie.trip.pojo.entity.TgTripPlanEntity;
import com.east.sea.genie.trip.pojo.vo.TgTripNodeVO;
import com.east.sea.genie.trip.pojo.vo.TgTripPlanVO;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface TgTripPlanService extends IService<TgTripPlanEntity> {

    TgTripPlanVO getTripPlan(Long id);

    Long create(TgTripPlanDTO planDTO);

    TgTripPlanVO generate(TgTripPlanDTO planDTO);
}
