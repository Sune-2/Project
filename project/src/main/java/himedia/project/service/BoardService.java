package himedia.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import himedia.project.dto.BoardRequestDto;
import himedia.project.dto.BoardResponseDto;
import himedia.project.entity.Board;
import himedia.project.entity.SearchCondition;
import himedia.project.exception.CustomException;
import himedia.project.exception.ErrorCode;
import himedia.project.repository.BoardRepository;
import himedia.project.repository.BoardRepositoyImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final BoardRepositoyImpl boardRepositoryImpl;
	
	// 게시글 생성
	@Transactional
	public void write(BoardRequestDto params) {
		boardRepository.save(params.toEntity());
	}
	
	// 게시글 리스트 조회
	// public List<BoardResponseDto> findAll() {
	//	  Sort sort = Sort.by(Direction.DESC, "id");
	//	  List<Board> list = boardRepository.findAll(sort);
	//	  return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
	// }
	
	// 게시글 리스트 페이징
	public Page<Board> getPage(int page) {
		Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
		return boardRepository.findAll(pageable);
	}
	
	// 게시글 수정
	@Transactional
	public Long update(Long id, BoardRequestDto params) {
		boardRepositoryImpl.update(id, params);
		// Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
		// entity.update(params.getTitle(), params.getContent(), params.getWriter());
		return id;
	}
	
	// 게시글 조회수 증가
	@Transactional
	public BoardResponseDto increaseHits(Long id) {
		boardRepositoryImpl.hits(id);
		Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
		// entity.increaseHits();
		return new BoardResponseDto(entity);
	}
	
	// 게시글 상세정보 조회
	public BoardResponseDto findById(Long id) {
		Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
		return new BoardResponseDto(entity);
	}
	
	@Transactional
	// 게시글 삭제
	public void delete(Long id) {
		boardRepositoryImpl.delete(id);
	}
	
	// 게시글 검색
	public List<Board> findBoard(SearchCondition searchCondition) {
	    return boardRepositoryImpl.search(searchCondition);
	}
}
