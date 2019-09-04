package com.digio.app.service.impl;

import com.digio.app.service.SignService;
import com.digio.app.vendor.digio.Response;
import com.digio.app.vendor.digio.Signder;
import com.digio.app.web.rest.errors.InternalServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for signing Documents.
 */
@Service
@Transactional
public class DigioSignServiceImpl implements SignService {

    private final Logger log = LoggerFactory.getLogger(DigioSignServiceImpl.class);

    @Value("${digigo.client-id}")
    private String CLIENT_ID;

    @Value("${digigo.client_secret}")
    private String CLIENT_SECRET;

    @Value("${digigo.base-url}")
    private String BASE_URL;

    @Value("${digigo.sign-url}")
    private String SIGN_URL;


    @Override
    public void sign(String userIdentity, MultipartFile file) {

        HttpHeaders headers = getHeaders();

        MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
        body.add("file",getFile(file));
        body.add("request",getSigner(userIdentity));
        HttpEntity<MultiValueMap<String, Object>> request  = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<Response> response = restTemplate.exchange( BASE_URL +SIGN_URL, HttpMethod.POST,request,Response.class);
        }catch (RestClientException ex){
            ex.printStackTrace();
            throw new InternalServerErrorException("Error From Digio");
        }
    }

    private HttpEntity<byte[]> getFile(MultipartFile file) {
        try {
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(file.getOriginalFilename())
                .build();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            return new HttpEntity<byte[]>(file.getBytes(), fileMap);
        } catch (IOException e) {
            log.debug("Can not read from the file");
            e.printStackTrace();
            throw new InternalServerErrorException("Can not read from the file");
        }
    }

    private String getSigner(String userIdentity){
        List signers = Arrays.asList(new Signder(userIdentity));
        try {
            Map<String,List<Signder>> result = new HashMap();
            result.put("signers",signers);
            return new ObjectMapper().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            log.debug("Invalid user identity");
            e.printStackTrace();
            throw new InternalServerErrorException("Invalid user identity");
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );
        headers.add("Authorization",authHeader);
        return headers;
    }

}
