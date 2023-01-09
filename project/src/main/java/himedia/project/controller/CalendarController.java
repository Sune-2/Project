package himedia.project.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import himedia.project.dto.PetDto;
import himedia.project.entity.Gender;
import himedia.project.entity.Pet;
import himedia.project.service.MemberService;
import himedia.project.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CalendarController {
	
	private final PetService petService;
	private final MemberService memberService;
	
	// 캘린더 페이지
	@GetMapping("/calendar")
	public String calendar(Model model, Authentication authentication) {
		List<Pet> list = petService.getListEvent();
		
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		
		HashMap<String, Object> hash = new HashMap<>();
		
		for (int idx = 0; idx < list.size(); idx++) {
			
			if (authentication != null && authentication.getName().equals(list.get(idx).getUserId())) {
				hash.put("title", list.get(idx).getUserId());
				hash.put("color", "#FF0000");
			}
			else {
				hash.put("title", "예약 완료");
				hash.put("color", "#3788D8");				
			}
			hash.put("start", list.get(idx).getReservateDate());
		
			jsonObj = new JSONObject(hash);
			jsonArr.add(jsonObj);
		}
	 	
		model.addAttribute("event",jsonArr);
		return "calendar/calendar";
	}
	
	// 예약 페이지
	@GetMapping("/reservate/{date}")
	public String reservationDate(@PathVariable String date, Principal principal, Model model) {
		model.addAttribute("member", memberService.getMember(principal.getName()));
		model.addAttribute("date", date);
		model.addAttribute("pet", PetDto.builder().build());
		return "calendar/reservate";
	}
	
	// 예약하기
	@PostMapping("/reservate")
	public String reservate(@ModelAttribute("pet") PetDto params, Principal principal, BindingResult bindingResult, Model model) {
		if (petService.findReservationInfo(principal.getName(), params.getReservateDate().toString()) != null) {
			bindingResult.rejectValue("reservateDate", "duplicateError", "이미 예약된 날짜입니다.");
			model.addAttribute("member", memberService.getMember(principal.getName()));
			model.addAttribute("date", params.getReservateDate());
			return "calendar/reservate";
		}
		petService.reservate(params);
		return "redirect:/";
	}
	
	// 예약 상세 페이지
	@GetMapping("/reservate/check/{date}")
	public String reservationCheck(@PathVariable String date, Principal principal, Model model) {
		model.addAttribute("pet", petService.findReservationInfo(principal.getName(), date));
		log.info("reservateDate : " + petService.findReservationInfo(principal.getName(), date).getReservateDate());
		return "calendar/check";
	}
	
	@PostMapping("reservate/cancel/{date}")
	public String reservationCancel(@PathVariable String date, Principal principal) {
		petService.cancelReservation(principal.getName(), date);
		return "redirect:/calendar";
	}
	
	@ModelAttribute("gender")
	public Gender[] gender() {
		return Gender.values();
	}
	
}
