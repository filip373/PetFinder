package com.petfinder.rest.controller;

import com.petfinder.domain.Advertisement;
import com.petfinder.rest.domain.RestResponse;
import com.petfinder.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisement")
public class AdvertisementRestController {

    @Autowired
    AdvertisementService advertisementService;

    @RequestMapping(value = "latest", method = RequestMethod.GET)
    public ResponseEntity<?> getLatest() {
        ResponseEntity<List<Advertisement>> response = null;
        try {
            response = new ResponseEntity<>(
                    advertisementService.getLatestAdvertisements(1),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new RestResponse(e.getMessage(), 1, "unexpected error"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return response;
    }

    @RequestMapping(value = "latest/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getLatest(
            @PathVariable int page) {
        ResponseEntity<List<Advertisement>> response = null;
        try {
             response = new ResponseEntity<>(
                    advertisementService.getLatestAdvertisements(page),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new RestResponse(e.getMessage(), 1, "unexpected error"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return response;
    }
}
