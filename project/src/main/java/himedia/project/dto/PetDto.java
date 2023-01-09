package himedia.project.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import himedia.project.entity.Gender;
import himedia.project.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PetDto {
	
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservateDate;
	private String userId;
	private String petName;
	private int petAge;
	@NotBlank(message = "성별은 필수 입력사항입니다.")
	private Gender gender;
	private String phone;
	private String address;
	private String content;
	
	public Pet toEntity() {
		return Pet.builder()
				.reservateDate(reservateDate)
				.userId(userId)
				.petName(petName)
				.petAge(petAge)
				.gender(gender)
				.phone(phone)
				.address(address)
				.content(content)
				.build();
	}
}
