package com.example.oauth.global.security.oauth.dto.userinfo;

import com.example.oauth.domain.member.entity.Provider;

public interface OAuthUserInfo {

    Long getSocialId();
    String getNickname();
    String getProfileImage();
    Provider getProvider();

}
