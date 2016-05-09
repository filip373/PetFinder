package com.petfinder.service;

import com.petfinder.dao.AdvertisementRepository;
import com.petfinder.dao.LocationRepository;
import com.petfinder.dao.PetRepository;
import com.petfinder.dao.PetCategoryRepository;
import com.petfinder.domain.Advertisement;
import com.petfinder.domain.Attachment;
import com.petfinder.domain.Location;
import com.petfinder.domain.Pet;
import com.petfinder.domain.PetCategory;
import com.petfinder.domain.Tag;
import com.petfinder.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AdvertisementService {

    @Autowired
    AdvertisementRepository advertisementRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    PetCategoryRepository petCategoryRepository;
    @Autowired
    PetRepository petRepository;

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

    @Transactional
    public void newAdvertisement(String title, String content, String petName, Integer age, String race, String categoryName, String voivodership, String commune, String place) {
        //TODO user, tags, attachments
        User user = null;
        PetCategory petCategory = new PetCategory(categoryName);
        petCategoryRepository.save(petCategory);
        Pet pet = new Pet(petName, race, age, user, petCategory);
        petRepository.save(pet);
        Location location= new Location(voivodership, place, commune);
        locationRepository.save(location);
       List<Tag> tags=null;
       List<Attachment> attachments=null;
        Advertisement advertisement= new Advertisement(title, content, user, pet, location, tags, attachments);
        advertisementRepository.save(advertisement);
    }
}
