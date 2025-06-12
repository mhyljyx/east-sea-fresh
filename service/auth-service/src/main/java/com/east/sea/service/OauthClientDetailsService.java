package com.east.sea.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.east.sea.pojo.entity.OauthClientDetailsEntity;
import com.east.sea.pojo.params.OauthClientDetailsQueryParams;
import com.east.sea.pojo.view.OauthClientDetailsView;

/**
 * @author tztang
 * @since 2025/06/11
 */
public interface OauthClientDetailsService extends IService<OauthClientDetailsEntity> {

    //Oauth客户端信息分页查询
    IPage<OauthClientDetailsView> query(OauthClientDetailsQueryParams params);

}
