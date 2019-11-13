package com.intentfilter.profileservice.controllers;

import com.intentfilter.profileservice.models.Profile;
import com.intentfilter.profileservice.services.ProfileService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
class ProfileControllerTest {
    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController controller;

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
}