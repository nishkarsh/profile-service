package com.intentfilter.profileservice.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class FileStorageServiceTest {
    @Autowired
    private FileStorageService service;

    @Test
    void shouldStoreFile() {
        final var sample = new MockMultipartFile("sample.jpg", "sample.jpg", "image/jpg", "something in this world".getBytes());

        final var path = service.storeFile(sample);

        assertNotNull(path);
    }
}