package com.intentfilter.profileservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intentfilter.profileservice.models.Profile;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(RandomBeansExtension.class)
class ProfileControllerIntegrationTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Profile.class);
    }

    @Test
    void shouldCreateProfile(@Random Profile profile) throws Exception {
        mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(profile)))
                .andExpect(status().isCreated());

        final var profiles = mongoTemplate.findAll(Profile.class);

        assertThat(profiles.size(), is(1));
    }

    @Test
    void shouldUpdateProfile(@Random Profile profile) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(profile)))
                .andExpect(status().isCreated())
                .andReturn();

        final var createdProfileJson = ((ObjectNode) objectMapper.readTree(mvcResult.getResponse().getContentAsString()));
        final var createdProfileId = createdProfileJson.get("id").asText();
        final var updatedProfileJson = createdProfileJson
                .put("displayName", "Mr. Foo")
                .put("height", 1.25);

        mockMvc.perform(put("/profile/" + createdProfileId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(updatedProfileJson)))
                .andExpect(status().isNoContent());

        final var persistedProfile = mongoTemplate.findById(createdProfileId, Profile.class);
        assertNotNull(persistedProfile);
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Profile.class);
    }
}