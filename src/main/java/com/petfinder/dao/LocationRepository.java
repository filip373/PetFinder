package com.petfinder.dao;

import com.petfinder.domain.Location;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {

    @Transactional
    List<Location> findByVoivodership(String voivodership);

    @Transactional
    List<Location> findByPlace(String place);

    @Transactional
    List<Location> findByPlaceAndCommune(String place, String commune);

    @Transactional
    List<Location> findByVoivodershipAndPlace(String voivodership,
                                              String place);

    @Transactional
    List<Location> findByVoivodershipAndPlaceAndCommune(String voivodership,
                                                        String place,
                                                        String commune);
    @Transactional
    List<Location> findByVoivodershipContainingOrPlaceContainingOrCommuneContaining(
		String voivodership,
        String place,
        String commune
    );
}
