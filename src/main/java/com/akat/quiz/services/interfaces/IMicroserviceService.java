package com.akat.quiz.services.interfaces;

public interface IMicroserviceService {

    <T> T makeWebClientRequest(String url, Class<T> responseType);
}
