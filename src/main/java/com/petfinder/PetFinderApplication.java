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
                    title, "Test", anames[rand.nextInt(anames.length)],
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
