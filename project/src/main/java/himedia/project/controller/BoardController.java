package himedia.project.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import himedia.project.dto.BoardRequestDto;
import himedia.project.service.BoardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	// 게시글 생성
	@PostMapping("/board/write")
	public String save(@Valid BoardRequestDto params, Principal principal) {
		params.setWriter(principal.getName());
		boardService.write(params);
		return "redirect:/board/list";
	}
	
	// 게시글 수정
	@PostMapping("/board/edit/{id}")
	public String save(@PathVariable Long id, @ModelAttribute BoardRequestDto params) {
		boardService.update(id, params);
		return "redirect:/board/view/{id}";
	}
	
	// 게시글 삭제
	@GetMapping("/board/delete/{id}")
	public String delete(@PathVariable Long id) {
		boardService.delete(id);
		return "redirect:/board/list";
	}
	
}
