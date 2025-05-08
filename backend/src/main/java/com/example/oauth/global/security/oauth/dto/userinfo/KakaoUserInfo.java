package com.example.oauth.global.security.oauth.dto.userinfo;

import com.example.oauth.domain.member.entity.Provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo implements OAuthUserInfo {

    @JsonProperty("id")
    private Long socialId;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class KakaoAccount {

        private Profile profile;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Profile {

            private String nickname;

            @JsonProperty("thumbnail_image_url")
            private String profileImage;

        }

    }

    @Override
    public Long getSocialId() {
        return this.socialId;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile.getNickname();
    }

    @Override
    public String getProfileImage() {
        return kakaoAccount.profile.getProfileImage();
    }

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }

}
