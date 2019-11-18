package com.intentfilter.profileservice.controllers;

import com.intentfilter.profileservice.models.FilePath;
import com.intentfilter.profileservice.models.Profile;
import com.intentfilter.profileservice.services.FileStorageService;
import com.intentfilter.profileservice.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private ProfileService profileService;
    private FileStorageService fileStorageService;

    public ProfileController(ProfileService profileService, FileStorageService fileStorageService) {
        this.profileService = profileService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable("id") String profileId) {
        return profileService.findById(profileId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Profile> create(@RequestBody @Valid Profile profile) {
        final var createdProfile = profileService.create(profile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody @Valid Profile profile) {
        profile.setId(id);
        profileService.update(profile);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/picture")
    public ResponseEntity<FilePath> uploadPicture(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(fileStorageService.storeFile(file), HttpStatus.CREATED);
    }
}
