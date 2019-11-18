package com.intentfilter.profileservice.services;

import com.intentfilter.profileservice.config.FileStoreConfig;
import com.intentfilter.profileservice.models.FilePath;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private FileStoreConfig config;

    public FileStorageService(FileStoreConfig config) {
        this.config = config;
    }

    public FilePath storeFile(MultipartFile file) {
        String filename = RandomStringUtils.randomAlphabetic(10) + StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }

            final var path = Paths.get(config.getUploadDir()).resolve(filename);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            }

            return new FilePath(filename);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }
}
