package himedia.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import himedia.project.dto.PetDto;
import himedia.project.entity.Pet;
import himedia.project.repository.PetRepository;
import himedia.project.repository.PetRepositoryImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {

	private final PetRepository petRepository;
	private final PetRepositoryImpl petRepositoryImpl;
	
	// 예약하기
	public Long reservate(PetDto params) {
		Pet entity = petRepository.save(params.toEntity());
		return entity.getId();
	}

	// 예약 정보 가져오기
	public List<Pet> getListEvent() {
		return petRepository.findAll();
	}
	
	// 예약 정보
	public Pet findReservationInfo(String userId, String date) {
		return petRepositoryImpl.findReservationByUserId(userId, date);
	}
	
	// 예약 취소
	@Transactional
	public void cancelReservation(String userId, String date) {
		petRepositoryImpl.cancel(userId, date);
	}
	
}
