package himedia.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import himedia.project.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
