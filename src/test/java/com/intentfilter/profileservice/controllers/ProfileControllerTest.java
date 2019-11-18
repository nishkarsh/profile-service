package com.intentfilter.profileservice.controllers;

import com.intentfilter.profileservice.models.FilePath;
import com.intentfilter.profileservice.models.Profile;
import com.intentfilter.profileservice.services.FileStorageService;
import com.intentfilter.profileservice.services.ProfileService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
class ProfileControllerTest {
    @Mock
    private ProfileService profileService;
    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private ProfileController controller;

    @Captor
    private ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);

    @Test
    void shouldGetProfile(@Random String profileId, @Random Profile persistedProfile) {
        when(profileService.findById(profileId)).thenReturn(Optional.of(persistedProfile));

        final var response = this.controller.getProfile(profileId);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(persistedProfile));
    }

    @Test
    void shouldReturnNotFoundStatusWhenProfileDoesNotExist(@Random String profileId) {
        when(this.profileService.findById(profileId)).thenReturn(Optional.empty());

        final var response = this.controller.getProfile(profileId);

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
    void shouldUpdateProfile(@Random Profile profile, @Random String id) {
        final var response = this.controller.update(id, profile);

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        verify(profileService, times(1)).update(captor.capture());
        Profile profileBeingUpdated = captor.getValue();
        assertThat(profileBeingUpdated.getId(), is(id));
    }

    @Test
    void shouldStoreProfilePicture() {
        final var file = new MockMultipartFile("mrkoo.jpg", "mrkoo.jpg", MediaType.IMAGE_JPEG_VALUE, "asmartyphoto".getBytes());
        final var uploadPath = new FilePath("mrkoo.jpg");
        when(this.fileStorageService.storeFile(file)).thenReturn(uploadPath);

        final var responseEntity = this.controller.uploadPicture(file);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(responseEntity.getBody(), is(uploadPath));
    }
}