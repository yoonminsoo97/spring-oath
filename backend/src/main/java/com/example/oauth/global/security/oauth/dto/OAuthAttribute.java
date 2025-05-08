package com.example.oauth.global.security.oauth.dto;

import com.example.oauth.global.security.oauth.dto.userinfo.KakaoUserInfo;
import com.example.oauth.global.security.oauth.dto.userinfo.OAuthUserInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttribute {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static OAuthUserInfo extract(Map<String, Object> attributes, String registrationId) {
        return kakao(attributes);
    }

    private static OAuthUserInfo kakao(Map<String, Object> attributes) {
        return objectMapper.convertValue(attributes, KakaoUserInfo.class);
    }

}
