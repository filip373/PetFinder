package com.petfinder.dao;

import com.petfinder.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface AdvertisementRepository
        extends CrudRepository<Advertisement, Long> {

    @Transactional
    @Query("SELECT a FROM Advertisement a ORDER BY createdDate DESC")
    List<Advertisement> findLatest();

    @Transactional
    @Query("SELECT a FROM Advertisement a ORDER BY createdDate DESC")
    List<Advertisement> findLatest(Pageable pageable);

    @Transactional
    @Query("SELECT a FROM Advertisement a " +
            "INNER JOIN advertisement_tags a_t " +
            "ON a_t.advertisement_id = a.id " +
            "INNER JOIN Tag t " +
            "ON t.id = a_t.tag_id " +
            "WHERE t IN :tags " +
            "ORDER BY a.createdDate DESC")
    List<Advertisement> findLatestInTags(@Param("tags") Collection<Tag> tags);

    @Transactional
    @Query("SELECT a FROM Advertisement a " +
            "INNER JOIN advertisement_tags a_t " +
            "ON a_t.advertisement_id = a.id " +
            "INNER JOIN Tag t " +
            "ON t.id = a_t.tag_id " +
            "WHERE t IN :tags " +
            "ORDER BY a.createdDate DESC")
    List<Advertisement> findLatestInTags(@Param("tags") Collection<Tag> tags,
                                         Pageable pageable);

    @Transactional
    List<Advertisement> findByUser(User user);

    @Transactional
    List<Advertisement> findByUser(User user, Pageable pageable);

    @Transactional
    List<Advertisement> findByPet(Pet pet);

    @Transactional
    List<Advertisement> findByPet(Pet pet, Pageable pageable);

    @Transactional
    List<Advertisement> findByLocation(Location location);

    @Transactional
    List<Advertisement> findByLocation(Location location, Pageable pageable);

    @Transactional
    List<Advertisement> findByTitleContaining(String title);

    @Transactional
    List<Advertisement> findByTitleContaining(String title, Pageable pageable);
}
