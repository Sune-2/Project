package himedia.project.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import himedia.project.dto.MemberDto;
import himedia.project.entity.Member;
import himedia.project.entity.Role;
import queryDsl.himedia.project.entity.QMember;

@Repository
// @Slf4j
public class MemberRepositoryImpl extends QuerydslRepositorySupport{
	
	private final JPAQueryFactory jpaQueryFactory;
	QMember member = QMember.member;
	
	public MemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		super(Member.class);
		this.jpaQueryFactory = jpaQueryFactory;
	}
	
	public void update(String userId, MemberDto update) {
		// log.info(update.getPassword());
		jpaQueryFactory.update(member)
				.set(member.password, update.getPassword())
				.set(member.phone, update.getPhone())
				.set(member.email, update.getEmail())
				.set(member.address, update.getAddress())
				.where(member.userId.eq(userId))
				.execute();
	}
	
//	public void findByMemberUserId(String userId) {
//		jpaQueryFactory.select(member.userId)
//				.from(member)
//				.where(member.userId.eq(userId))
//				.fetchOne();
//	}
	
	public void authority(Long id) {
		jpaQueryFactory.update(member)
				.set(member.role, Role.manager)
				.where(member.id.eq(id))
				.execute();
	}
}
