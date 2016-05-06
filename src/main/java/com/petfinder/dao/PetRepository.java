package com.petfinder.dao;

import com.petfinder.domain.Pet;
import com.petfinder.domain.PetCategory;
import com.petfinder.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PetRepository extends CrudRepository<Pet, Long> {

    @Transactional
    List<Pet> findByName(String name);

    @Transactional
    List<Pet> findByName(String name, Pageable pageable);

    @Transactional
    List<Pet> findByNameContaining(String name);

    @Transactional
    List<Pet> findByNameContaining(String name, Pageable pageable);

    @Transactional
    List<Pet> findByRace(String race);

    @Transactional
    List<Pet> findByRace(String race, Pageable pageable);

    @Transactional
    List<Pet> findByOwner(User owner);

    @Transactional
    List<Pet> findByOwner(User owner, Pageable pageable);

    @Transactional
    List<Pet> findByCategory(PetCategory category);

    @Transactional
    List<Pet> findByCategory(PetCategory category, Pageable pageable);
}
