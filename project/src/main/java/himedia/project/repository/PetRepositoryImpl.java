package himedia.project.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import himedia.project.entity.Pet;
import queryDsl.himedia.project.entity.QPet;

@Repository
// @Slf4j
public class PetRepositoryImpl extends QuerydslRepositorySupport {
	
	private final JPAQueryFactory jpaQueryFactory;
	QPet pet = QPet.pet;
	
	public PetRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		super(Pet.class);
		this.jpaQueryFactory = jpaQueryFactory;
	}
	
	public Pet findReservationByUserId(String userId, String date) {
		StringTemplate dateFormatted = Expressions.stringTemplate
				("PARSEDATETIME('{0s}', '{1s}')", date
				, ConstantImpl.create("yyyy-MM-dd"));
		
		return jpaQueryFactory.selectFrom(pet)
					.where(pet.userId.eq(userId)
					.and(pet.reservateDate.eq(dateFormatted)))
					.fetchOne();
	}
	
	public void cancel(String userId, String date) {
		StringTemplate dateFormatted = Expressions.stringTemplate
				("PARSEDATETIME('{0s}', '{1s}')", date
				, ConstantImpl.create("yyyy-MM-dd"));
		
		jpaQueryFactory.delete(pet)
				.where(pet.userId.eq(userId)
				.and(pet.reservateDate.eq(dateFormatted)))
				.execute();
	}
}


