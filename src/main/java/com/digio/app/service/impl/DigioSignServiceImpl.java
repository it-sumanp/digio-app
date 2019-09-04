package com.digio.app.service.impl;

import com.digio.app.service.SignService;
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

        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
            .builder("form-data")
            .name("file")
            .filename(file.getOriginalFilename())
            .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = null;
        try {
            fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);
        body.add("request", "{\"signers\":[{\"identifier\":\"adada@afa.com\"}]}");


        HttpEntity<MultiValueMap<String, Object>> request  = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        try{
            ResponseEntity<Object> response = restTemplate
                .postForEntity(BASE_URL +SIGN_URL, request, Object.class);

            if(response.getStatusCode().is2xxSuccessful()){
                log.debug("ok");
            }
        }catch (RestClientException e){
            e.getMessage();
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
