/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petfinder.rest.domain;

import com.petfinder.domain.Attachment;
import com.petfinder.domain.Tag;
import java.util.List;

/**
 *
 * @author AndriY
 */
public class AddAdvertisementForm {

    private String content;
    private String petName;
    private Integer age;
    private String race;
    private String categoryName;
    private String voivodership;
    private String commune;
    private String place;
    private String title;
    private List<Tag> tags;
    private List<Attachment> attachments;

    public AddAdvertisementForm() {
    }

    public AddAdvertisementForm(String content, String petName, Integer age, String race, String categoryName, String voivodership, String commune, String place, String title, List<Tag> tags, List<Attachment> attachments) {
        this.content = content;
        this.petName = petName;
        this.age = age;
        this.race = race;
        this.categoryName = categoryName;
        this.voivodership = voivodership;
        this.commune = commune;
        this.place = place;
        this.title = title;
        this.tags = tags;
        this.attachments = attachments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getVoivodership() {
        return voivodership;
    }

    public void setVoivodership(String voivodership) {
        this.voivodership = voivodership;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
