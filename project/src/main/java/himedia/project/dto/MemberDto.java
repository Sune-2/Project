package himedia.project.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import himedia.project.entity.Gender;
import himedia.project.entity.Member;
import himedia.project.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberDto {
	
	private Long id;			// 고유 아이디
	@NotBlank(message = "아이디는 필수 입력사항입니다.")	
	@Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 소문자와 숫자만 사용가능하며, 4~10글자여야 합니다.")
	private String userId;		// 유저 아이디
	@NotBlank(message = "비밀번호는 필수 입력사항입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 1개 이상포함해야하며, 8~16자리여야 합니다.")
	private String password;	// 비밀번호
	@NotBlank(message = "비밀번호 확인은 필수 입력사항입니다.")
	private String passwordCheck;
	@NotBlank(message = "이름은 필수 입력사항입니다.")
	private String userName;	// 유저 이름
	@NotBlank(message = "성별은 필수 입력사항입니다.")
	private Gender gender;		// 성별
	@NotBlank(message = "생년월일은 필수 입력사항입니다.")
	private String birthday;	// 생년월일
	@NotBlank(message = "연락처는 필수 입력사항입니다.")
	private String phone;		// 연락처
	@NotBlank(message = "이메일은 필수 입력사항입니다.")
	@Email
	private String email;		// 이메일
	private String address;		// 주소
	private Role role;			// 회원등급
	
	public Member toEntity() {
		return Member.builder()
				.userId(userId)
				.password(password)
				.userName(userName)
				.gender(gender)
				.birthday(birthday)
				.phone(phone)
				.email(email)
				.address(address)
				.role(Role.general)
				.build();
	}
	
	public void passwordEncoding(String encodingPassword) {
		this.password = encodingPassword;
	}
}
