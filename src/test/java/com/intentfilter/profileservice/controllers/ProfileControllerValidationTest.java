package com.intentfilter.profileservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intentfilter.profileservice.models.Location;
import com.intentfilter.profileservice.models.Profile;
import com.intentfilter.profileservice.models.ProfileBuilder;
import com.intentfilter.profileservice.services.ProfileService;
import io.github.glytching.junit.extension.random.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerValidationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProfileService profileService;

    @Test
    void shouldReturnBadRequestIfDisplayNameNotPresent() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();
        FieldUtils.writeField(profile, "displayName", "", true);

        performRequestAndAssertBadRequest(profile);
    }

    @Test
    void shouldReturnBadRequestIfDisplayNameExceeds256Chars() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();
        final var displayName = RandomStringUtils.random(257);
        FieldUtils.writeField(profile, "displayName", displayName, true);

        performRequestAndAssertBadRequest(profile);
    }

    @Test
    void shouldReturnBadRequestIfActualNameNotPresent() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();
        FieldUtils.writeField(profile, "actualFullName", "", true);

        performRequestAndAssertBadRequest(profile);
    }

    @Test
    void shouldReturnBadRequestIfActualNameExceeds256Chars() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();
        final var displayName = RandomStringUtils.random(257);
        FieldUtils.writeField(profile, "actualFullName", displayName, true);

        performRequestAndAssertBadRequest(profile);
    }

    @Test
    void shouldReturnBadRequestIfBirthdayNotPresentInRequest() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();
        FieldUtils.writeField(profile, "birthday", null, true);

        performRequestAndAssertBadRequest(profile);
    }

    @Test
    void shouldReturnBadRequestIfMaritalStatusNotPresentInRequest() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();
        FieldUtils.writeField(profile, "maritalStatusId", null, true);

        performRequestAndAssertBadRequest(profile);
    }

    @Test
    void shouldReturnBadRequestIfOccupationExceeds256Chars() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();
        final var occupation = RandomStringUtils.random(257);
        FieldUtils.writeField(profile, "occupation", occupation, true);

        performRequestAndAssertBadRequest(profile);
    }

    @Test
    void shouldReturnBadRequestIfAboutMeDescriptionExceeds5000Chars() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();
        final var aboutMe = RandomStringUtils.random(5001);
        FieldUtils.writeField(profile, "aboutMe", aboutMe, true);

        performRequestAndAssertBadRequest(profile);
    }

    @Test
    void shouldReturnBadRequestIfLocationNotPresentInRequest() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();
        FieldUtils.writeField(profile, "location", null, true);

        performRequestAndAssertBadRequest(profile);
    }

    @Test
    void shouldReturnOkIfAllDataValid() throws Exception {
        final var profile = ProfileBuilder.withDefaults().build();

        mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(profile)))
                .andExpect(status().isCreated());
    }

    private void performRequestAndAssertBadRequest(@Random Profile profile) throws Exception {
        mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(profile)))
                .andExpect(status().isBadRequest());
    }
}
