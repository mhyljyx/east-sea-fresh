package com.east.sea.genie.social.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.east.sea.genie.social.pojo.dto.TgInteractionDTO;
import com.east.sea.genie.social.pojo.entity.TgInteractionEntity;

public interface TgInteractionService extends IService<TgInteractionEntity> {

    Boolean action(TgInteractionDTO interactionDTO);
}
