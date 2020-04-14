package com.akat.quiz.services.impl;

import com.akat.quiz.services.interfaces.IMicroserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MicroserviceService implements IMicroserviceService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public <T> T makeRequest(String url,  Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }
}
