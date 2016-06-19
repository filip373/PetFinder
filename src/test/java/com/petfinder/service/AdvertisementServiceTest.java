package com.petfinder.service;

import com.petfinder.PetFinderApplication;
import com.petfinder.dao.*;
import com.petfinder.domain.*;
import com.petfinder.exception.UserDoesNotHavePermissionToAdvertisementException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = PetFinderApplication.class)
public class AdvertisementServiceTest {

    @Spy
    private AdvertisementService advertisementService = new AdvertisementService();

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetCategoryRepository petCategoryRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        advertisementService.advertisementRepository = advertisementRepository;
        advertisementService.userRepository = userRepository;
        advertisementService.userService = userService;
        advertisementService.petRepository = petRepository;
        advertisementService.locationRepository = locationRepository;
        advertisementService.tagRepository = tagRepository;
        advertisementService.petCategoryRepository = petCategoryRepository;
    }

    // #9 test case
    @Test
    public void shouldGetAllAdvertisements() throws Exception {
        List<Advertisement> adList = new ArrayList<>();
        User user = new User();
        Pet pet = new Pet();
        Location location = new Location();
        List<Tag> tags = new ArrayList<>();
        List<Attachment> attachments = new ArrayList<>();
        String title1 = "title1";
        String title2 = "title2";
        String title3 = "title3";
        adList.add(new Advertisement(title1, "content", user, pet, location, tags, attachments));
        adList.add(new Advertisement(title2, "content", user, pet, location, tags, attachments));
        adList.add(new Advertisement(title3, "content", user, pet, location, tags, attachments));
        Page<Advertisement> page = new PageImpl<>(adList);
        when(advertisementRepository.findByIsDeletedOrderByCreatedDate(any(Boolean.class), any(Pageable.class))).thenReturn(page);

        List<Advertisement> advertisements = advertisementService.getLatestAdvertisements(1, 20);

        assertThat(advertisements.size(), is(3));
        assertThat(advertisements.get(0).getTitle(), is(title1));
        assertThat(advertisements.get(1).getTitle(), is(title2));
        assertThat(advertisements.get(2).getTitle(), is(title3));
    }

    // #17 test case
    @Test
    public void shouldAddNewAdvertisement() throws Exception {
        String title = "title";
        String content = "content";
        String name = "name";
        int age = 4;
        String race = "black";
        String categoryName = "2";
        String voivodership = "voivodership";
        String commune = "commune";
        String place = "place";
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag("2");
        String tagName = "tag";
        tag.setName(tagName);
        tags.add(tag);
        PetCategory petCategory = new PetCategory();
        Pet pet = new Pet();
        User user = new User();
        Location location = new Location();
        List<Attachment> attachments = new ArrayList<>();
        when(userRepository.findOneByLogin(any())).thenReturn(user);
        when(userService.getLoggedUserName()).thenReturn("User");
        when(petCategoryRepository.findOne(Long.parseLong(categoryName))).thenReturn(petCategory);
        when(petRepository.findByNameAndRaceAndCategoryAndOwner(eq(name), eq(race), eq(petCategory), eq(user))).thenReturn(new ArrayList<Pet>(){{add(pet);}});
        when(locationRepository.findByVoivodershipAndPlaceAndCommune(eq(voivodership), eq(place), eq(commune))).thenReturn(new ArrayList<Location>(){{add(location);}});
        when(tagRepository.findOneByName(tagName)).thenReturn(tag);
        ArgumentCaptor<Advertisement> captor = ArgumentCaptor.forClass(Advertisement.class);

        advertisementService.newAdvertisement(title, content, name, age, race, categoryName, voivodership, commune, place, tags, attachments);

        verify(advertisementRepository, times(1)).save(any(Advertisement.class));
        verify(advertisementRepository).save(captor.capture());
        assertThat(captor.getValue().getTitle(), is(title));
        assertThat(captor.getValue().getContent(), is(content));
        assertThat(captor.getValue().getTitle(), is(title));
        assertThat(captor.getValue().getPet(), is(pet));
        assertThat(captor.getValue().getLocation(), is(location));
        assertThat(captor.getValue().getTags(), is(tags));
        assertThat(captor.getValue().getUser(), is(user));
        assertThat(captor.getValue().getAttachments(), is(attachments));
    }

    // #18 test case
    @Test(expected = NumberFormatException.class)
    public void shouldNotAddNewAdvertisementWhenCategoryInvalid() throws Exception {
        String title = "title";
        String content = "content";
        String name = "name";
        int age = 4;
        String race = "black";
        String categoryName = "category";
        String voivodership = "voivodership";
        String commune = "commune";
        String place = "place";
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag("2");
        String tagName = "tag";
        tag.setName(tagName);
        tags.add(tag);
        PetCategory petCategory = new PetCategory();
        Pet pet = new Pet();
        User user = new User();
        Location location = new Location();
        List<Attachment> attachments = new ArrayList<>();
        when(userRepository.findOneByLogin(any())).thenReturn(user);
        when(userService.getLoggedUserName()).thenReturn("User");
        when(petCategoryRepository.findOne(any())).thenReturn(petCategory);
        when(petRepository.findByNameAndRaceAndCategoryAndOwner(eq(name), eq(race), eq(petCategory), eq(user))).thenReturn(new ArrayList<Pet>(){{add(pet);}});
        when(locationRepository.findByVoivodershipAndPlaceAndCommune(eq(voivodership), eq(place), eq(commune))).thenReturn(new ArrayList<Location>(){{add(location);}});
        when(tagRepository.findOneByName(tagName)).thenReturn(tag);

        advertisementService.newAdvertisement(title, content, name, age, race, categoryName, voivodership, commune, place, tags, attachments);
    }

    // #19 test case
    @Test
    public void shouldEditAdvertisement() throws Exception {
        User user = new User();
        String login = "user";
        user.setLogin(login);
        Advertisement old = new Advertisement("title", "content", user, new Pet(), new Location(), new ArrayList<>(), new ArrayList<>());
        Long id = 1l;
        String title = "title2";
        String content = "content2";
        String name = "name";
        int age = 4;
        String race = "black";
        String categoryName = "1";
        String voivodership = "voivodership";
        String commune = "commune";
        String place = "place";
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag("2");
        String tagName = "tag";
        tag.setName(tagName);
        tags.add(tag);
        PetCategory petCategory = new PetCategory();
        Pet pet = new Pet();
        pet.setName(name);
        Location location = new Location();
        List<Attachment> attachments = new ArrayList<>();
        when(advertisementRepository.findOne(id)).thenReturn(old);
        when(userService.getLoggedUserName()).thenReturn(login);
        when(petCategoryRepository.findOne(Long.parseLong(categoryName))).thenReturn(petCategory);
        when(petRepository.findByNameAndRaceAndCategoryAndOwner(eq(name), eq(race), eq(petCategory), eq(user))).thenReturn(new ArrayList<Pet>(){{add(pet);}});
        when(locationRepository.findByVoivodershipAndPlaceAndCommune(eq(voivodership), eq(place), eq(commune))).thenReturn(new ArrayList<Location>(){{add(location);}});
        when(tagRepository.findOneByName(tagName)).thenReturn(tag);
        ArgumentCaptor<Advertisement> captor = ArgumentCaptor.forClass(Advertisement.class);

        advertisementService.editAdvertisement(id, title, content, name, age, race, categoryName, voivodership, commune, place, tags, attachments);

        verify(advertisementRepository, times(1)).save(any(Advertisement.class));
        verify(advertisementRepository).save(captor.capture());
        assertThat(captor.getValue().getTitle(), is(title));
        assertThat(captor.getValue().getContent(), is(content));
        assertThat(captor.getValue().getTitle(), is(title));
        assertThat(captor.getValue().getPet().getName(), is(pet.getName()));
        assertThat(captor.getValue().getLocation(), is(location));
        assertThat(captor.getValue().getTags(), is(tags));
        assertThat(captor.getValue().getUser(), is(user));
        assertThat(captor.getValue().getAttachments(), is(attachments));
    }

    // #20 test case
    @Test(expected = NumberFormatException.class)
    public void shouldNotEditAdvertisementWhenCategoryInvalid() throws Exception {
        User user = new User();
        String login = "user";
        user.setLogin(login);
        Advertisement old = new Advertisement("title", "content", user, new Pet(), new Location(), new ArrayList<>(), new ArrayList<>());
        Long id = 1l;
        String title = "title2";
        String content = "content2";
        String name = "name";
        int age = 4;
        String race = "black";
        String categoryName = "category";
        String voivodership = "voivodership";
        String commune = "commune";
        String place = "place";
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag("2");
        String tagName = "tag";
        tag.setName(tagName);
        tags.add(tag);
        PetCategory petCategory = new PetCategory();
        Pet pet = new Pet();
        pet.setName(name);
        Location location = new Location();
        List<Attachment> attachments = new ArrayList<>();
        when(advertisementRepository.findOne(id)).thenReturn(old);
        when(userService.getLoggedUserName()).thenReturn(login);
        when(petCategoryRepository.findOne(any())).thenReturn(petCategory);
        when(petRepository.findByNameAndRaceAndCategoryAndOwner(eq(name), eq(race), eq(petCategory), eq(user))).thenReturn(new ArrayList<Pet>(){{add(pet);}});
        when(locationRepository.findByVoivodershipAndPlaceAndCommune(eq(voivodership), eq(place), eq(commune))).thenReturn(new ArrayList<Location>(){{add(location);}});
        when(tagRepository.findOneByName(tagName)).thenReturn(tag);

        advertisementService.editAdvertisement(id, title, content, name, age, race, categoryName, voivodership, commune, place, tags, attachments);
    }

    // #21 test case
    @Test
    public void shouldDeleteAdvertisement() throws Exception {
        Long advertisementId = 1l;
        User user = new User();
        String login = "user";
        user.setLogin(login);
        String title = "title";
        Advertisement advertisement = new Advertisement(title, null, user, new Pet(), new Location(), new ArrayList<>(), new ArrayList<>());
        when(advertisementRepository.findOne(advertisementId)).thenReturn(advertisement);
        when(userService.getLoggedUserName()).thenReturn(login);
        ArgumentCaptor<Advertisement> captor = ArgumentCaptor.forClass(Advertisement.class);

        advertisementService.deleteAdvertisement(advertisementId);

        verify(advertisementRepository, times(1)).delete(any(Advertisement.class));
        verify(advertisementRepository).delete(captor.capture());
        assertThat(captor.getValue().getTitle(), is(title));
    }

    // #22 test case
    @Test(expected = UserDoesNotHavePermissionToAdvertisementException.class)
    public void shouldNotEditAdvertisementOfOtherUser() throws Exception {
        User userOwner = new User();
        userOwner.setLogin("user");
        Advertisement old = new Advertisement("title", "content", userOwner, new Pet(), new Location(), new ArrayList<>(), new ArrayList<>());
        String editorLogin = "user2";
        Long id = 1l;
        String title = "title2";
        String content = "content2";
        String name = "name";
        int age = 4;
        String race = "black";
        String categoryName = "1";
        String voivodership = "voivodership";
        String commune = "commune";
        String place = "place";
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag("2");
        String tagName = "tag";
        tag.setName(tagName);
        tags.add(tag);
        Pet pet = new Pet();
        pet.setName(name);
        List<Attachment> attachments = new ArrayList<>();

        when(advertisementRepository.findOne(id)).thenReturn(old);
        when(userService.getLoggedUserName()).thenReturn(editorLogin);

        advertisementService.editAdvertisement(id, title, content, name, age, race, categoryName, voivodership, commune, place, tags, attachments);
    }

    // #23 test case
    @Test(expected = UserDoesNotHavePermissionToAdvertisementException.class)
    public void shouldNotDeleteAdvertisementOfOtherUser() throws Exception {
        Long advertisementId = 1l;
        User owner = new User();
        String ownerlogin = "owner";
        owner.setLogin(ownerlogin);
        String otherLogin = "other";
        Advertisement advertisement = new Advertisement(null, null, owner, new Pet(), new Location(), new ArrayList<>(), new ArrayList<>());
        when(advertisementRepository.findOne(advertisementId)).thenReturn(advertisement);
        when(userService.getLoggedUserName()).thenReturn(otherLogin);

        advertisementService.deleteAdvertisement(advertisementId);
    }
}