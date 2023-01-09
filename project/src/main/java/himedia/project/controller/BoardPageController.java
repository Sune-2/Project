package himedia.project.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import himedia.project.dto.BoardResponseDto;
import himedia.project.entity.Board;
import himedia.project.entity.SearchCondition;
import himedia.project.entity.SearchType;
import himedia.project.service.BoardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardPageController {
	
	private final BoardService boardService;
	
	// 게시글 리스트 페이지
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Board> paging = boardService.getPage(page);
		model.addAttribute("searchType", SearchType.values());
		model.addAttribute("paging", paging);
		return "board/list";
	}
	
	// 게시글 등록 페이지
	@GetMapping("/write")
	public String write(@RequestParam(required = false) Long id, Model model) {
		model.addAttribute("id", id);
		return "board/write";
	}
	
	// 게시글 상세 페이지
	@GetMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model) {
		BoardResponseDto board = boardService.increaseHits(id);
		model.addAttribute("board", board);
		return "board/view";
	}
	
	// 게시글 수정 페이지
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		BoardResponseDto board = boardService.findById(id);
		model.addAttribute("board", board);
		return "board/edit";
	}
	
	// 게시글 검색
	@GetMapping("/search")
	public String search(SearchType searchType, String keyword, Model model) {
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		model.addAttribute("board", boardService.findBoard(new SearchCondition(keyword, searchType)));
		return "board/search";
		
	}
}
