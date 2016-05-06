package com.petfinder.dao;

import com.petfinder.domain.Tag;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface TagRepository extends CrudRepository<Tag, Long> {

    @Transactional
    Tag findOneByName(String name);
}
