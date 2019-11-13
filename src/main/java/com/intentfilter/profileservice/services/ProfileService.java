package com.intentfilter.profileservice.services;

import com.intentfilter.profileservice.models.Profile;
import com.intentfilter.profileservice.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public Profile create(Profile profile) {
        return repository.insert(profile);
    }
}
