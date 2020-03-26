package com.akat.quiz.controllers;

import com.akat.quiz.model.entities.Quiz;
import com.akat.quiz.model.security.User;
import com.akat.quiz.model.security.UserDto;
import com.akat.quiz.model.security.enums.RoleType;
import com.akat.quiz.services.interfaces.IMainService;
import com.akat.quiz.services.interfaces.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {

    private final IMainService mainService;

    @Autowired
    public MainController(IMainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping
    public Quiz test(){
        return mainService.test();
    }

    @GetMapping("/all-quizzes")
    public List<Quiz> getAll() {
        return mainService.getAllQuizzes();
    }

    @PostMapping("/create-quiz")
    @ResponseStatus(HttpStatus.CREATED)
    public Quiz createQuiz(@RequestBody Quiz quiz ) {
        return mainService.saveQuiz(quiz);
    }

    @GetMapping("/get-quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Quiz getQuizById(@PathVariable("id") Long id){
        return mainService.getQuizById(id);
    }

    @DeleteMapping("/delete-quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        mainService.deleteQuizById(id);
    }

    @PutMapping("/update-quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuiz(@PathVariable("id")Long id, @RequestBody Quiz quiz) {
        mainService.updateQuiz(id, quiz);
    }
}
