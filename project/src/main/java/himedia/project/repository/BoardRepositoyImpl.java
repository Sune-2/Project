package himedia.project.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import himedia.project.dto.BoardRequestDto;
import himedia.project.entity.Board;
import himedia.project.entity.SearchCondition;
import himedia.project.entity.SearchType;
import queryDsl.himedia.project.entity.QBoard;

@Repository
public class BoardRepositoyImpl extends QuerydslRepositorySupport {

	private final JPAQueryFactory jpaQueryFactory;
	QBoard board = QBoard.board;
	
	public BoardRepositoyImpl(JPAQueryFactory jpaQueryFactory) {
		super(Board.class);
		this.jpaQueryFactory = jpaQueryFactory;
	}
	
	public void hits(Long id) {
		jpaQueryFactory.update(board)
				.set(board.hits, board.hits.add(1))
				.where(board.id.eq(id))
				.execute();	
	}
	
	public void update(Long id, BoardRequestDto update) {
		LocalDateTime updateDate = LocalDateTime.now(); 
		
		jpaQueryFactory.update(board)
				.set(board.title, update.getTitle())
				.set(board.content, update.getContent())
				.set(board.updateDate, updateDate)
				.where(board.id.eq(id))
				.execute();
	}
	
	public void delete(Long id) {
		jpaQueryFactory.delete(board)
				.where(board.id.eq(id))
				.execute();
	}
	
	public List<Board> search(SearchCondition condition) {
		return jpaQueryFactory.selectFrom(board)
					.where(isSearchable(condition.type, condition.content))
					.orderBy(board.createDate.desc())
					.fetch();
	}
	
	BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> s) {
		try {
			return new BooleanBuilder(s.get());
		} catch (Exception e) {
			return new BooleanBuilder();
		}
	}
	
	BooleanBuilder userEq(String content) {
		return nullSafeBuilder(() -> board.writer.eq(content));
	}
	
	BooleanBuilder titleCt(String content) {
		return nullSafeBuilder(() -> board.title.contains(content));
	}
	
	BooleanBuilder contentCt(String content) {
		return nullSafeBuilder(() -> board.content.contains(content));
	}
	
	BooleanBuilder isSearchable(SearchType searchType, String content) {
		if (searchType == SearchType.TITLE) {
			return titleCt(content);
		} else if (searchType == SearchType.WRITER) {
			return userEq(content);
		} else {
			return titleCt(content).or(contentCt(content));
		}
	}

}
