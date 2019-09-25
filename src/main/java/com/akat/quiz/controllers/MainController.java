package com.akat.quiz.controllers;

import com.akat.quiz.model.Answer;
import com.akat.quiz.model.Question;
import com.akat.quiz.model.Quiz;
import com.akat.quiz.services.interfaces.IMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class MainController {

    private final IMainService service;

    @Autowired
    public MainController(IMainService service) {
        this.service = service;
    }

    @GetMapping
    public void test(){
        service.test();
    }

    @GetMapping("/now")
    public String currentTime() {
        return service.whatTimeIsIt();
    }

    @GetMapping("/all-quizzes")
    public List<Quiz> getAll() {
        return service.getAllQuizzes();
    }

    @PostMapping("/create-quiz")
    @ResponseStatus(HttpStatus.CREATED)
    public Quiz createQuiz(@RequestBody Quiz quiz ) {
        return service.saveQuiz(quiz);
    }


    @DeleteMapping("/delete-quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
//        service.deleteQuizById()
    }

    @PutMapping("update-quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuiz(@PathVariable("id")Long id, @RequestBody Quiz quiz) {
//        service.updateQuiz(quiz);
    }
}
