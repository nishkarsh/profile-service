package com.intentfilter.profileservice.services;

import com.intentfilter.profileservice.models.Profile;
import com.intentfilter.profileservice.repositories.ProfileRepository;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
class ProfileServiceTest {
    @Mock
    private ProfileRepository repository;

    @InjectMocks
    private ProfileService service;

    @Test
    void shouldCreateProfile(@Random Profile profile, @Mock Profile insertedProfile) {
        when(this.repository.insert(profile)).thenReturn(insertedProfile);

        final var createdProfile = this.service.create(profile);

        assertThat(createdProfile, is(insertedProfile));
    }

    @Test
    void shouldUpdateProfile(@Random Profile profile, @Mock Profile updatedProfile) {
        when(this.repository.save(profile)).thenReturn(updatedProfile);

        this.service.update(profile);

        verify(this.repository).save(profile);
    }
}