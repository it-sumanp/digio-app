package com.digio.app.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for signing Documents.
 */
public interface SignService {

    /**
     * Sign a file.
     *
     * @param file the file to sign
     */
    void sign(String userKey,MultipartFile file);
}
