package com.petfinder.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location extends AbstractPersistable<Long>
        implements Serializable {

    @Column(name = "voivodership", nullable = false)
    private String voivodership;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "commune")
    private String commune;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    private List<Advertisement> advertisements;

    public Location() {
        super();
    }

    public Location(String voivodership, String place) {
        super();
        this.voivodership = voivodership;
        this.place = place;
        this.advertisements = new ArrayList<>();
    }

    public Location(String voivodership, String place, String commune) {
        super();
        this.voivodership = voivodership;
        this.place = place;
        this.commune = commune;
        this.advertisements = new ArrayList<>();
    }

    public String getVoivodership() {
        return voivodership;
    }

    public void setVoivodership(String voivodership) {
        this.voivodership = voivodership;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
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
                "Location<#%d, voivodership=%s, place=%s, commune=%s>",
                getId(),
                getVoivodership(),
                getPlace(),
                getCommune()
        );
    }
}
