package com.petfinder;

import com.petfinder.dao.AdvertisementRepository;
import com.petfinder.dao.LocationRepository;
import com.petfinder.dao.UserRepository;
import com.petfinder.domain.Advertisement;
import com.petfinder.domain.Location;
import com.petfinder.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@SpringApplicationConfiguration(PetFinderApplication.class)
@IntegrationTest
public class AdvertisementDatabaseIT {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    // case #10 (modified)
    @Test
    public void shouldFilterAdvertisementsByTitle() throws Exception {
        List<Advertisement> advertisements = advertisementRepository.findByTitleContaining("POMOCY");

        assertThat(advertisements.size(), is(6));
        for (int i = 0; i < 6; i++) {
            assertThat(advertisements.get(i).getTitle().contains("POMOCY"), is(true));
        }
    }

    // case #11 (modified)
    @Test
    public void shouldFilterAdvertisementsByLocation() throws Exception {
        Location location = locationRepository.findByVoivodership("Szczecin").get(0);

        List<Advertisement> advertisements = advertisementRepository.findByLocation(location);

        assertThat(advertisements.size(), is(2));
    }

    // case #12 (modified)
    @Test
    public void shouldFilterAdvertisementsByUser() throws Exception {
        User user = userRepository.findOneByLogin("user1");

        List<Advertisement> advertisements = advertisementRepository.findByUser(user);

        assertThat(advertisements.size(), is(13));
    }
}
