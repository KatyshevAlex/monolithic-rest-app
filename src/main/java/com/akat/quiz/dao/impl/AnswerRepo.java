package com.akat.quiz.dao.impl;

import com.akat.quiz.model.Answer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("production")
public interface AnswerRepo extends JpaRepository<Answer, Long> {

//    @Query("SELECT q FROM Questions f WHERE LOWER(f.name) = LOWER(:name)")
//    Question getQuestion();
//
//    @Query("SELECT now()")
//    String now();
}
