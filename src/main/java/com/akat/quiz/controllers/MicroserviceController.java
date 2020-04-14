package com.akat.quiz.controllers;

import com.akat.quiz.model.entities.Quiz;
import com.akat.quiz.services.interfaces.IMicroserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/microservice")
@CrossOrigin(origins = "http://localhost:3000")
public class MicroserviceController {

    @Autowired
    IMicroserviceService microserviceService;

    @GetMapping
    public Quiz asyncRequestWithWebClient(){
        return microserviceService.makeWebClientRequest("http://localhost:8081/quiz", Quiz.class);
    }
}
