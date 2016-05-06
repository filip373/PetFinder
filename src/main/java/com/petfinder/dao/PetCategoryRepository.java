package com.petfinder.dao;

import com.petfinder.domain.PetCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PetCategoryRepository
        extends CrudRepository<PetCategory, Long> {

    @Transactional
    List<PetCategory> findByName(String name);

    @Transactional
    List<PetCategory> findByName(String name, Pageable pageable);
}
