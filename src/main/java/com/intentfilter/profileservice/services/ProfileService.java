package com.intentfilter.profileservice.services;

import com.intentfilter.profileservice.models.Profile;
import com.intentfilter.profileservice.repositories.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public Profile create(Profile profile) {
        logger.info("Attempting to create a profile");

        final var insertedProfile = repository.insert(profile);

        logger.info("Created profile with ID [{}]", insertedProfile.getId());
        return insertedProfile;
    }

    public void update(Profile profile) {
        logger.info("Attempting to update profile with ID [{}]", profile.getId());

        repository.save(profile);

        logger.info("Updated profile with ID [{}]", profile.getId());
    }
}
