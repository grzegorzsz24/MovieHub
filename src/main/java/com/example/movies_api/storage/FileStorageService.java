package com.example.movies_api.storage;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final String fileStorageLocation;
    private final String imageStorageLocation;

    public FileStorageService(@Value("${app.storage.location}") String storageLocation) throws FileNotFoundException {
        this.fileStorageLocation = storageLocation + "/files/";
        this.imageStorageLocation = storageLocation + "/img/";
        Path fileStoragePath = Path.of(this.fileStorageLocation);
        checkDirectoryExists(fileStoragePath);
        Path imageStoragePath = Path.of(this.imageStorageLocation);
        checkDirectoryExists(imageStoragePath);
    }

    private void checkDirectoryExists(Path path) throws FileNotFoundException {
        if (Files.notExists(path)) {
            throw new FileNotFoundException("Directory %s does not exist.".formatted(path.toString()));
        }
    }

    public String saveImage(MultipartFile file) {
        return saveFile(file, imageStorageLocation);
    }

    public String saveFile(MultipartFile file) {
        return saveFile(file, fileStorageLocation);
    }

    private String saveFile(MultipartFile file, String storageLocation) {
        Path filePath = createFilePath(file, storageLocation);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.getFileName().toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Path createFilePath(MultipartFile file, String storageLocation) {
        String originalFileName = file.getOriginalFilename();
        String fileBaseName = FilenameUtils.getBaseName(originalFileName);
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        String completeFilename;
        Path filePath;
        int fileIndex = 0;
        do {
            completeFilename = fileBaseName + fileIndex + "." + fileExtension;
            filePath = Paths.get(storageLocation, completeFilename);
            fileIndex++;
        } while (Files.exists(filePath));
        return filePath;
    }
}