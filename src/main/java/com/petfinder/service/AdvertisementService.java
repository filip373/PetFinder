package com.petfinder.service;

import com.petfinder.dao.AdvertisementRepository;
import com.petfinder.domain.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Transactional
    public List<Advertisement> getLatestAdvertisements(int page, int perPage) {
        List<Advertisement> advertisements
                = advertisementRepository.findByIsDeletedOrderByCreatedDate(
                false,
                new PageRequest(page, perPage)
        ).getContent();
        // Call relations to make sure those are delivered with advertisement.
        // It's required because of lazy loading of related entities, pretty
        // performance unfriendly, but still better than eager loading.
        for (Advertisement advertisement : advertisements) {
            advertisement.getAttachments().size();
            advertisement.getLocation();
            advertisement.getPet();
            advertisement.getTags().size();
            advertisement.getUser();
        }
        return advertisements;
    }

    @Transactional
    public List<Advertisement> getLatestAdvertisements(int page) {
        return getLatestAdvertisements(page, 20);
    }

    @Transactional
    public long getNumberOfPages(long perPage) {
        long pages = advertisementRepository.count();
        return (long) Math.ceil(pages / perPage);
    }
}
