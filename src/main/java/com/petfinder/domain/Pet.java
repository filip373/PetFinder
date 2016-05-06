package com.petfinder.domain;

import org.apache.log4j.Category;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
public class Pet extends AbstractPersistable<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "race", nullable = false)
    private String race;

    @Column(name = "age")
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PetCategory category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pet")
    private List<Advertisement> advertisements;

    public Pet() {
        super();
    }

    public Pet(String name, String race, Integer age, User owner,
               PetCategory category) {
        super();
        this.name = name;
        this.race = race;
        this.age = age;
        this.owner = owner;
        this.category = category;
        this.advertisements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public PetCategory getCategory() {
        return category;
    }

    public void setCategory(PetCategory category) {
        this.category = category;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    @Override
    public String toString() {
        return String.format(
                "Pet<#%d, name=%s, race=%s, age=%d, owner=%s, category=%s>",
                getId(),
                getName(),
                getRace(),
                getAge(),
                getOwner().toString(),
                getCategory().toString()
        );
    }
}
