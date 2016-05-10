package com.petfinder.controller;

import com.petfinder.domain.Attachment;
import com.petfinder.domain.Tag;
import com.petfinder.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdvertisementController {

    @Autowired
    AdvertisementService advertisementService;

    @RequestMapping(value = {"/", "/latest"})
    public String latestAdvertisements(Model model) {
        long pages = advertisementService.getNumberOfPages(20);
        if (pages < 1) {
            pages = 1;
        }

        long firstpage = 1;
        long lastpage = 3;
        if (lastpage > pages) {
            lastpage = pages;
        }

        List<Long> printPages = new ArrayList<>();
        for (long p = firstpage; p <= lastpage; p++) {
            printPages.add(p);
        }
        model.addAttribute("page", 10);
        model.addAttribute("pages", pages);
        model.addAttribute("firstpage", firstpage);
        model.addAttribute("lastpage", lastpage);
        model.addAttribute("printPages", printPages);
        model.addAttribute("advertisements",
                advertisementService.getLatestAdvertisements(0)
        );
        return "adlist";
    }

    @RequestMapping(value = "/latest/{page}")
    public String latestAdvertisements(@PathVariable int page, Model model) {
        long pages = advertisementService.getNumberOfPages(20);
        if (pages < 1) {
            pages = 1;
        }

        long firstpage = page - 2;
        if (firstpage < 1) {
            firstpage = 1;
        }

        long lastpage = page + 2;
        if (lastpage > pages) {
            lastpage = pages;
        }

        List<Long> printPages = new ArrayList<>();
        for (long p = firstpage; p <= lastpage; p++) {
            printPages.add(p);
        }

        model.addAttribute("page", page);
        model.addAttribute("pages", pages);
        model.addAttribute("firstpage", firstpage);
        model.addAttribute("lastpage", lastpage);
        model.addAttribute("printPages", printPages);
        model.addAttribute("advertisements",
                advertisementService.getLatestAdvertisements(page - 1)
        );
        return "adlist";
    }

    @RequestMapping(value = "/newAdd")
    public String newAdvertisement(@RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String petName,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String race,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String voivodership,
            @RequestParam(required = false) String commune,
            @RequestParam(required = false) String place,
            @RequestParam(required = false) String tagsString,
            @RequestParam(required = false) String attachmentType,
            @RequestParam(required = false) String attachmentUri,
            Model model) {
        if (content != null && petName != null && race != null && categoryName != null && voivodership != null && commune != null && place != null) {
            if (!content.equals("") && !petName.equals("") && !race.equals("") && !categoryName.equals("") && !voivodership.equals("") && !commune.equals("") && !place.equals("")) {

                List<String> listTags = new ArrayList<>(Arrays.asList(tagsString.split(",")));
                List<Tag> tags = new ArrayList<>();
                for (String listTag : listTags) {
                    tags.add(new Tag(listTag.trim()));
                }

                List<Attachment> attachments = new ArrayList<>();
                if ("image".equals(attachmentType)) {
                    attachments.add(new Attachment(attachmentUri, Attachment.Type.IMAGE, null));
                } else if ("video".equals(attachmentType)) {
                    attachments.add(new Attachment(attachmentUri, Attachment.Type.VIDEO, null));
                }
                advertisementService.newAdvertisement(title, content, petName, age, race, categoryName, voivodership, commune, place, tags, attachments);
                model.addAttribute("statusOK", "Avertisement has been added successfully.");
            } else {
                model.addAttribute("statusEmpty", "Fields cannot remain empty.");
               }
         }
        return "addAdvertisement";
    }
}
