package himedia.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import himedia.project.dto.MemberDto;
import himedia.project.entity.Member;
import himedia.project.entity.Role;
import himedia.project.repository.MemberRepository;
import himedia.project.repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	private final MemberRepositoryImpl memberRepositoryImpl;
	private final PasswordEncoder encoder;
	
	// 중복 회원 체크
//	public boolean joinCheck(MemberDto params) {
//		Member findMember = memberRepository.findByUserId(params.getUserId());
//		if (findMember == null) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	// 회원 가입
	@Transactional
	public Long join(MemberDto params) {
		params.passwordEncoding(encoder.encode(params.getPassword()));
		Member entity = memberRepository.save(params.toEntity());
		return entity.getId();
	}
	
	// 회원 정보 수정
	@Transactional
	public String updateMember(String userId, MemberDto params) {
		// Member entity = memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
		// entity.update(params.getPassword(), params.getPhone(), params.getEmail(), params.getAddress());
		params.passwordEncoding(encoder.encode(params.getPassword()));
		memberRepositoryImpl.update(userId, params);
		return userId;
	}
	
//	public void findByUserId(String userId) {
//		memberRepositoryImpl.findByMemberUserId(userId);
//	}
	
	public Member getMember(String userId) {
		Optional<Member> member = memberRepository.findByUserId(userId);
		return member.get();
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Optional<Member> _member = memberRepository.findByUserId(userId);
		if (_member.isEmpty()) {
			log.info("사용자를 찾을 수 없습니다.");
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + userId);
		}
		
		// log.info("사용자 검색 완료 {}", _member);
		
		Member member = _member.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if ("admin".equals(userId)) {
			authorities.add(new SimpleGrantedAuthority(Role.admin.getValue()));
		} else
			authorities.add(new SimpleGrantedAuthority(Role.general.getValue()));
		
		// log.info("권한부여 완료", _member);
		
		return User.builder()
				   .username(member.getUserId())
				   .password(member.getPassword())
				   .roles(member.getRole().getValue())
				   .build();
	}
	
}
