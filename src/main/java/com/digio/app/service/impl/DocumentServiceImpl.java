package com.digio.app.service.impl;

import com.digio.app.domain.User;
import com.digio.app.service.DocumentService;
import com.digio.app.service.SignService;
import com.digio.app.service.UserService;
import com.digio.app.utils.NullUtil;
import com.digio.app.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Service Implementation for managing Documents.
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final UserService userService;

    private final SignService signService;

    public DocumentServiceImpl(UserService userService, SignService signService) {
        this.userService = userService;
        this.signService = signService;
    }

    @Override
    public void signFile(MultipartFile file) {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        userOptional.ifPresent(user -> {
            if(NullUtil.isEmpty(user.getEmail())){
                throw new BadRequestAlertException("User Email Not Set", "User", "user-email-empty");
            }
            signService.sign(user.getEmail(),file);
        });
    }
}
