package himedia.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import himedia.project.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	// @Transactional
    // @Modifying
    // @Query("UPDATE Board b SET b.hits = b.hits + 1 WHERE b.id = :id")
	// void updateHits(@Param("id") Long id);	
}
