package com.petfinder;

import com.lyncode.jtwig.mvc.JtwigViewResolver;
import com.petfinder.domain.Tag;
import com.petfinder.service.AdvertisementService;
import com.petfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@PropertySource(value = { "classpath:application.properties" })
@EnableJpaRepositories
@EnableAutoConfiguration
@EnableTransactionManagement
public class PetFinderApplication implements CommandLineRunner {

    @Autowired
    AdvertisementService advertisementService = new AdvertisementService();
    
    @Autowired
    UserService userService = new UserService();    

	public static void main(String[] args) {
		SpringApplication.run(PetFinderApplication.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {
        Random rand = new Random();
        Tag atags[] = {
                new Tag("tag1"), new Tag("tag2"), new Tag("tag3"), new Tag("tag4"),
                new Tag("tag5"), new Tag("tag6"), new Tag("tag7"), new Tag("tag8"),
                new Tag("tag9"), new Tag("tag10"), new Tag("tag11"), new Tag("tag12")
        };
        String atitles[] = {
                "Zgubiono ", "Zaginął ", "POMOCY GDZIE JEST MÓJ ", "Znaleziono ",
                "Znaleziony ", "Odzyskano ", "Sprzedam opla i "
        };
        String apets[] = {
                "jeż", "pies", "kot", "żyrafa", "słoń", "leniwiec", "orangutan"
        };
        String anames[] = {
                "Henryk", "Andrzej", "Manuela", "Bogdan", "Wiesiek", "Bożydar"
        };
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            tags.clear();
            int tc = rand.nextInt(5);
            for (int j = 0; j < tc; j++) {
                tags.add(atags[rand.nextInt(atags.length)]);
            }
            String pet = apets[rand.nextInt(apets.length)];
            String title = String.format("%s%s", atitles[rand.nextInt(atitles.length)],
                    pet);
            advertisementService.newAdvertisement(
                    title,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas feugiat in tortor sed egestas. Nulla id interdum enim, quis commodo est. Donec tempor, quam vitae sodales scelerisque, quam velit feugiat elit, nec ultrices lectus velit non quam. Sed eleifend, eros nec scelerisque ultricies, lectus libero laoreet lectus, eu luctus sem odio non felis. Pellentesque vitae orci quis tortor egestas dapibus at maximus lacus. Vestibulum varius diam euismod nisi tristique ornare. Ut tincidunt, arcu nec sodales vestibulum, turpis ante condimentum turpis, id venenatis urna massa sed ex. Mauris ante magna, luctus sit amet est vitae, accumsan tempor enim. Fusce neque purus, scelerisque ut porttitor posuere, molestie sit amet magna. Morbi eget aliquam leo. Sed quis placerat nisl, eu aliquam diam. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nam sagittis ligula a vestibulum congue. Nam mattis volutpat est eget pharetra. Sed consectetur blandit molestie. ",
                    anames[rand.nextInt(anames.length)],
                    rand.nextInt(20), pet, "tojestbezsensu",
                    "Łódzkie", "Widzew", "Łódź", tags, new ArrayList<>()
            );
        }
    }

    @Bean
    public ViewResolver viewResolver() {
        JtwigViewResolver viewResolver = new JtwigViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".twig");
        return viewResolver;
    }

    @Bean
    public AdvertisementService advertisementService() {
        return this.advertisementService;
    }
    
    @Bean
    public UserService userService() {
        return this.userService;
    }
}
