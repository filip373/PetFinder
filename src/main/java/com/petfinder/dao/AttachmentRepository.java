package com.petfinder.dao;

import com.petfinder.domain.Advertisement;
import com.petfinder.domain.Attachment;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

    @Transactional
    List<Attachment> findByAdvertisement(Advertisement advertisement);
}
