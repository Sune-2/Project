package himedia.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import himedia.project.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	// UserId로 회원 검색
	public Optional<Member> findByUserId(String userId);
}
