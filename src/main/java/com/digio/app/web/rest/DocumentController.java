package com.digio.app.web.rest;

import com.digio.app.service.DocumentService;
import com.digio.app.utils.NullUtil;
import com.digio.app.web.rest.errors.BadRequestAlertException;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for Managing Document.
 */
@RestController
@RequestMapping("/api")
public class DocumentController {

    private final Logger log = LoggerFactory.getLogger(DocumentController.class);

    private static final String ENTITY_NAME = "document";

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }


    @PostMapping("/upload")
    @Timed
    public ResponseEntity<Void> uploadFile(
        @RequestParam(name = "file", required = true) MultipartFile file
    ) {
        log.debug("Uploading File ", file.getOriginalFilename());
        if(NullUtil.isEmpty(file.getOriginalFilename())) {
            throw new BadRequestAlertException("Empty File Name Not Accepted", ENTITY_NAME, "empty-file-name");
        }

        if(!file.getOriginalFilename().endsWith(".pdf")){
            throw new BadRequestAlertException("Only PDF Files Accepted", ENTITY_NAME, "pdf-file-only");
        }

        documentService.signFile(file);

        return null;
    }

}
