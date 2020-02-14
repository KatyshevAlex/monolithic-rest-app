package com.akat.quiz.dao.impl;

import com.akat.quiz.annotations.LogExecutionTime;
import com.akat.quiz.model.Answer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("production")
public interface AnswerRepo extends JpaRepository<Answer, Long> {

    @LogExecutionTime
    @Query(value = "SELECT * FROM answers a WHERE a.question_id = :question_id",
            nativeQuery = true)
    List<Answer> getAllAnswersByQuestionId(@Param("question_id")Long questionId);
}
