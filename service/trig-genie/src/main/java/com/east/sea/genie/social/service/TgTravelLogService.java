package com.east.sea.genie.social.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.east.sea.genie.social.pojo.dto.TgSocialFeedDTO;
import com.east.sea.genie.social.pojo.dto.TgTravelLogDTO;
import com.east.sea.genie.social.pojo.entity.TgTravelLogEntity;
import com.east.sea.genie.social.pojo.vo.TgTravelLogVO;

public interface TgTravelLogService extends IService<TgTravelLogEntity> {

    Boolean publish(TgTravelLogDTO logDTO);

    Page<TgTravelLogVO> feed(TgSocialFeedDTO socialFeedDTO);
}
