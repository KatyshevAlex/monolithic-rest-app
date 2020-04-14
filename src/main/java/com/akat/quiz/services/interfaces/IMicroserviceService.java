package com.akat.quiz.services.interfaces;

public interface IMicroserviceService {

    <T> T makeRequest(String url,  Class<T> responseType);
}
