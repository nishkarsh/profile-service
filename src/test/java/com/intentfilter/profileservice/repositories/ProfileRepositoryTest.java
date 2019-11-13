package com.intentfilter.profileservice.repositories;

import com.intentfilter.profileservice.models.Profile;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
class ProfileRepositoryTest {
    @Autowired
    private ProfileRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION = "ps.profiles";

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(COLLECTION);
    }

    @Test
    void shouldInsertProfile(@Random Profile profile) {
        Profile insertedProfile = repository.insert(profile);

        Optional<Profile> foundProfile = repository.findById(insertedProfile.getId());

        assertTrue(foundProfile.isPresent());
        assertThat(foundProfile.get(), is(insertedProfile));
    }

    @Test
    void shouldUpdateProfile(@Random Profile profile, @Random Profile updatedProfile) {
        Profile insertedProfile = repository.insert(profile);

        updatedProfile.setId(insertedProfile.getId());
        updatedProfile.setVersion(insertedProfile.getVersion());
        repository.save(updatedProfile);

        Optional<Profile> foundProfile = repository.findById(insertedProfile.getId());

        assertTrue(foundProfile.isPresent());
        assertThat(foundProfile.get(), is(updatedProfile));
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(COLLECTION);
    }
}