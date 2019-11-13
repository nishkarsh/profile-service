package com.intentfilter.profileservice.controllers;

import com.intentfilter.profileservice.models.Profile;
import com.intentfilter.profileservice.services.ProfileService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable("id") String profileId) {
        return service.findById(new ObjectId(profileId))
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Profile> create(Profile profile) {
        final var createdProfile = service.create(profile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody Profile profile) {
        profile.setId(new ObjectId(id));
        service.update(profile);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
