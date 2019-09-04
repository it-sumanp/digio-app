package com.digio.app.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing Documents.
 */
public interface DocumentService {

    /**
     * Sign a file.
     *
     * @param file the file to upload
     */
    void signFile(MultipartFile file);
}
