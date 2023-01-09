package himedia.project.controller;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import himedia.project.dto.MemberDto;
import himedia.project.entity.Gender;
import himedia.project.entity.Member;
import himedia.project.form.JoinForm;
import himedia.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	
	// 회원가입
	@GetMapping("/join")
	public String join(Model model) {
		model.addAttribute("dto", MemberDto.builder().build());
		return "member/join";
	}
	
	// 회원가입 - POST
	@PostMapping("/join")
	public String joinPost(@Valid @ModelAttribute("dto") JoinForm form, BindingResult bindingResult) {
//		if (memberService.joinCheck(params))
//			memberService.join(params);
		if (bindingResult.hasErrors()) {
			return "member/join";
		}
		
		if (!form.getPassword().equals(form.getPasswordCheck())) {
			bindingResult.rejectValue("passwordCheck", "passwordError", "패스워드가 일치하지 않습니다.");
			return "member/join";
		}
		
		MemberDto dto = MemberDto.builder()
				.userId(form.getUserId())
				.password(form.getPassword())
				.userName(form.getUserName())
				.gender(form.getGender())
				.birthday(form.getBirthday())
				.phone(form.getPhone())
				.email(form.getEmail())
				.address(form.getAddress())
				.build();
		
		try {
			memberService.join(dto);			
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("joinError", "이미 등록된 회원입니다.");
			return "member/join";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("joinError", e.getMessage());
			return "member/join";
		}
		return "redirect:/";
	}
	
	@ModelAttribute("gender")
	public Gender[] gender() {
		return Gender.values();
	}
	
	// 로그인
	@GetMapping("/login")
	public String login() {
		return "member/login";
	}
	
	// 회원정보 수정
	@GetMapping("/modify")
	public String modify(Principal principal, Model model) {
		log.info("principal.getName() " + principal.getName());
		String userId = principal.getName();
		Member member = memberService.getMember(userId);
		model.addAttribute("member", member);
		return "member/modify";
	}
	
	// 회원정보 수정
	@PostMapping("/modify")
	public String update(Principal principal, @ModelAttribute("member") MemberDto dto, BindingResult bindingResult) {
		String userId = principal.getName();
		
		if (!dto.getPassword().equals(dto.getPasswordCheck())) {
			bindingResult.rejectValue("passwordCheck", "passwordError", "패스워드가 일치하지 않습니다.");
			return "member/modify";
		}
		
		// log.info(dto.getPassword());
		memberService.updateMember(userId, dto);
		return "redirect:/";
	}
}
