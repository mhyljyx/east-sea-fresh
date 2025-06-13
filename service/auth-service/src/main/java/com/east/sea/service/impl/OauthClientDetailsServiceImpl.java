package com.east.sea.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.annotation.EditType;
import com.east.sea.enums.BaseCode;
import com.east.sea.exception.ArgumentException;
import com.east.sea.exception.BusinessFrameException;
import com.east.sea.mapper.OauthClientDetailsMapper;
import com.east.sea.pojo.entity.OauthClientDetailsEntity;
import com.east.sea.pojo.params.OauthClientDetailsDelParams;
import com.east.sea.pojo.params.OauthClientDetailsParams;
import com.east.sea.pojo.params.OauthClientDetailsQueryParams;
import com.east.sea.pojo.view.OauthClientDetailsView;
import com.east.sea.service.OauthClientDetailsService;
import com.east.sea.util.CopyUtil;
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
        return oauthClientDetailsEntityPage.convert(entity -> {
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
    }

    @Override
    public void add(OauthClientDetailsParams params) {
        OauthClientDetailsEntity oauthClientDetailsEntity = new OauthClientDetailsEntity();
        CopyUtil.copyProperties(params, oauthClientDetailsEntity, EditType.INSERT);
        this.baseMapper.insert(oauthClientDetailsEntity);
    }

    @Override
    public void update(OauthClientDetailsParams params) {
        boolean exists = this.baseMapper.exists(
                Wrappers.<OauthClientDetailsEntity>lambdaQuery()
                        .eq(OauthClientDetailsEntity::getClientId, params.getClientId())
        );
        if (!exists) {
            throw new BusinessFrameException(BaseCode.OAUTH_CLIENT_NOT_EXIST.getCode(), BaseCode.OAUTH_CLIENT_NOT_EXIST.getMsg());
        }
        OauthClientDetailsEntity oauthClientDetailsEntity = new OauthClientDetailsEntity();
        CopyUtil.copyProperties(params, oauthClientDetailsEntity, EditType.MODIFY);
        this.baseMapper.updateById(oauthClientDetailsEntity);
    }

    @Override
    public void del(OauthClientDetailsDelParams params) {
        int deletedCount = this.baseMapper.delete(
                Wrappers.<OauthClientDetailsEntity>lambdaQuery()
                        .in(OauthClientDetailsEntity::getClientId, params.getIds())
        );
        if (deletedCount == 0) {
            throw new BusinessFrameException(BaseCode.OAUTH_CLIENT_NOT_EXIST.getCode(), BaseCode.OAUTH_CLIENT_NOT_EXIST.getMsg());
        }
    }

    @Override
    public OauthClientDetailsView getInfo(String id) {
        OauthClientDetailsEntity oauthClientDetailsEntity = this.baseMapper.selectById(id);
        if (ObjectUtil.isNull(oauthClientDetailsEntity)) {
            throw new BusinessFrameException(BaseCode.OAUTH_CLIENT_NOT_EXIST.getCode(), BaseCode.OAUTH_CLIENT_NOT_EXIST.getMsg());
        }
        OauthClientDetailsView view = new OauthClientDetailsView();
        CopyUtil.copyProperties(oauthClientDetailsEntity, view);
        return view;
    }

}
