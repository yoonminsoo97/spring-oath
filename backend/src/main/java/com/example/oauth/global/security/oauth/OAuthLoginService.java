package com.example.oauth.global.security.oauth;

import com.example.oauth.domain.member.entity.Member;
import com.example.oauth.domain.member.repository.MemberRepository;
import com.example.oauth.global.security.oauth.dto.OAuthAttribute;
import com.example.oauth.global.security.oauth.dto.userinfo.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
@RequiredArgsConstructor
public class OAuthLoginService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
        String registrationId = getRegistrationId(userRequest);
        String userNameAttributeKey = getUserNameAttributeKey(userRequest);
        OAuthUserInfo oAuthUserInfo = OAuthAttribute.extract(attributes, registrationId);
        Member member = saveOrUpdate(oAuthUserInfo);
        return new DefaultOAuth2User(createAuthorityList(member.getAuthority()), attributes, userNameAttributeKey);
    }

    private String getUserNameAttributeKey(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
    }

    private String getRegistrationId(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration().getRegistrationId();
    }

    public Member saveOrUpdate(OAuthUserInfo oAuthUserInfo) {
        return memberRepository.findBySocialIdAndProvider(oAuthUserInfo.getSocialId(), oAuthUserInfo.getProvider())
                .map((member) -> {
                    member.update(oAuthUserInfo.getNickname(), oAuthUserInfo.getProfileImage());
                    return member;
                })
                .orElseGet(() -> {
                    Member member = Member.builder()
                            .socialId(oAuthUserInfo.getSocialId())
                            .nickname(oAuthUserInfo.getNickname())
                            .profileImage(oAuthUserInfo.getProfileImage())
                            .provider(oAuthUserInfo.getProvider())
                            .build();
                    return memberRepository.save(member);
                });
    }

}
