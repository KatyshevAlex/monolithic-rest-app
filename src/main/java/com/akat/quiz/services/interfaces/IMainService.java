package com.akat.quiz.services.interfaces;

import com.akat.quiz.model.Quiz;

import java.util.List;

public interface IMainService {
    Quiz test();

    String whatTimeIsIt();

    List<Quiz> getAllQuizzes();

    Quiz saveQuiz(Quiz quiz);

//    Answer save(Answer o);
}

