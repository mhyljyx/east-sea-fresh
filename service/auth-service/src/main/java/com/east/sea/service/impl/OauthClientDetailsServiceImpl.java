package com.east.sea.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.mapper.OauthClientDetailsMapper;
import com.east.sea.pojo.entity.OauthClientDetailsEntity;
import com.east.sea.pojo.params.OauthClientDetailsQueryParams;
import com.east.sea.pojo.view.OauthClientDetailsView;
import com.east.sea.service.OauthClientDetailsService;
import com.east.sea.util.PageUtil;
import org.springframework.stereotype.Service;

/**
 * @author tztang
 * @since 2025/06/11
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetailsEntity> implements OauthClientDetailsService {

    @Override
    public IPage<OauthClientDetailsView> query(OauthClientDetailsQueryParams params) {
        Page<OauthClientDetailsEntity> oauthClientDetailsEntityPage = this.baseMapper.selectPage(
                PageUtil.getPage(params),
                Wrappers.<OauthClientDetailsEntity>lambdaQuery()
                        .like(ObjectUtil.isNotNull(params.getQueryInfo()), OauthClientDetailsEntity::getClientId, params.getQueryInfo())
                        .or()
                        .like(ObjectUtil.isNotNull(params.getQueryInfo()), OauthClientDetailsEntity::getClientName, params.getQueryInfo())
        );
        IPage<OauthClientDetailsView> oauthClientDetailsViewIPage = oauthClientDetailsEntityPage.convert(entity -> {
            OauthClientDetailsView view = new OauthClientDetailsView();
            view.setClientId(entity.getClientId());
            view.setClientName(entity.getClientName());
            view.setScope(entity.getScope());
            view.setAuthorizedGrantTypes(entity.getAuthorizedGrantTypes());
            view.setWebServerRedirectUri(entity.getWebServerRedirectUri());
            view.setAccessTokenValidity(entity.getAccessTokenValidity());
            view.setRefreshTokenValidity(entity.getRefreshTokenValidity());
            view.setAdditionalInformation(entity.getAdditionalInformation());
            // 处理敏感信息
            view.setClientSecret(entity.getClientSecret());
            return view;
        });
        return oauthClientDetailsViewIPage;
    }

}
