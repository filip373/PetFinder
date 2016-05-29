package com.petfinder.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.petfinder.domain.Attachment;
import com.petfinder.domain.Tag;
import com.petfinder.service.AdvertisementService;

@Controller
public class AdvertisementController {

	private final static int INITIAL_PAGE = 0;
	
    @Autowired
    AdvertisementService advertisementService;

    @RequestMapping(value = {"/", "/latest"})
    public String latestAdvertisements(Model model) {
    	this.preparePagination(model, INITIAL_PAGE+1);
        model.addAttribute("advertisements",
                advertisementService.getLatestAdvertisements(INITIAL_PAGE)
        );
        return "adlist";
    }

    @RequestMapping(value = "/latest/{page}")
    public String latestAdvertisements(@PathVariable int page, Model model) {
        this.preparePagination(model, page);
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
	
	@ResponseBody
	@RequestMapping(value = "/searchResult")
	public ModelAndView getSearchResults( Model model,
        @RequestParam(required = false) String adInfo,
        @RequestParam(required = false) String petInfo,
        @RequestParam(required = false) String locationInfo,
        @RequestParam(required = false) String tagInfo,
        @RequestParam(required = false) int page
	) {
        model.addAttribute("advertisements",
                advertisementService.getSearchedAdvertisements(page-1, 20, adInfo, petInfo, locationInfo, tagInfo)
        );
        this.preparePagination(model, page);
        model.addAttribute("adInfo", adInfo);
        model.addAttribute("petInfo", petInfo);
        model.addAttribute("locationInfo", locationInfo);
        model.addAttribute("tagInfo", tagInfo);
        return new ModelAndView( "searchResults" );
    }
	
	private void preparePagination(Model model, int page)
	{
        long pages = advertisementService.getNumberOfPages(20);
        if (pages < 1) {
            pages = 1;
        }
        long firstpage = page == 1 ? 1 : page - 2;
        if (firstpage < 1) {
            firstpage = 1;
        }
        long lastpage = page == 1 ? 3 : page + 2;
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
	}
}
