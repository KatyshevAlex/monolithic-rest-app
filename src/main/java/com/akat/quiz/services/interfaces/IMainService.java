package com.akat.quiz.services.interfaces;

import com.akat.quiz.model.Quiz;

import java.util.List;

public interface IMainService {
    Quiz test();

    List<Quiz> getAllQuizzes();

    Quiz saveQuiz(Quiz quiz);

    void deleteQuizById(Long id);

    Quiz updateQuiz(Long id, Quiz quiz);
}