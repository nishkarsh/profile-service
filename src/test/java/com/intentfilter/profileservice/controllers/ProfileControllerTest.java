package com.intentfilter.profileservice.controllers;

import com.intentfilter.profileservice.models.Profile;
import com.intentfilter.profileservice.services.ProfileService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
class ProfileControllerTest {
    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController controller;

    @Captor
    private ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);

    @Test
    void shouldGetProfile(@Random ObjectId profileId, @Random Profile persistedProfile) {
        when(profileService.findById(profileId)).thenReturn(Optional.of(persistedProfile));

        final var response = this.controller.getProfile(profileId.toHexString());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(persistedProfile));
    }

    @Test
    void shouldReturnNotFoundStatusWhenProfileDoesNotExist(@Random ObjectId profileId) {
        when(this.profileService.findById(profileId)).thenReturn(Optional.empty());

        final var response = this.controller.getProfile(profileId.toHexString());

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldCreateProfile(@Random Profile profile, @Random Profile createdProfile) {
        when(profileService.create(profile)).thenReturn(createdProfile);

        final var responseEntity = controller.create(profile);

        assertThat(createdProfile, is(responseEntity.getBody()));
        verify(profileService, times(1)).create(profile);
    }

    @Test
    void shouldUpdateProfile(@Random Profile profile, @Random ObjectId id) {
        final var response = this.controller.update(id.toHexString(), profile);

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        verify(profileService, times(1)).update(captor.capture());
        Profile profileBeingUpdated = captor.getValue();
        assertThat(profileBeingUpdated.getId(), is(id));
    }
}