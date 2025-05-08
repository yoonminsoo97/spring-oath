package com.example.oauth.domain.member.repository;

import com.example.oauth.domain.member.entity.Member;

import com.example.oauth.domain.member.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialIdAndProvider(Long socialId, Provider provider);

}
