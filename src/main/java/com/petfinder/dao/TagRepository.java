package com.petfinder.dao;

import com.petfinder.domain.Tag;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {

    @Transactional
    Tag findOneByName(String name);
    
    @Transactional
    List<Tag> findByNameContaining(String name);
}
