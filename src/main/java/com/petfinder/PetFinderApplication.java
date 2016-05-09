package com.petfinder;

import com.petfinder.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;

import com.lyncode.jtwig.mvc.JtwigViewResolver;

@SpringBootApplication
@PropertySource(value = { "classpath:application.properties" })
@EnableJpaRepositories
@EnableAutoConfiguration
@EnableTransactionManagement
public class PetFinderApplication {

    @Autowired
    AdvertisementService advertisementService = new AdvertisementService();

    public static void main(String[] args) {
		SpringApplication.run(PetFinderApplication.class, args);
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
}
