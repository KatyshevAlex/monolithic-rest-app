package com.akat.quiz.services.interfaces;

import com.akat.quiz.annotations.LogExecutionTime;
import com.akat.quiz.model.entities.Quiz;

import java.util.List;

public interface IMainService {
    Quiz test();

    @LogExecutionTime
    List<Quiz> getAllQuizzes();

    @LogExecutionTime
    Quiz saveQuiz(Quiz quiz);

    void deleteQuizById(Long id);

    Quiz updateQuiz(Long id, Quiz quiz);

    Quiz getQuizById(Long id);
}
