package com.akat.quiz.dao.impl;

import com.akat.quiz.model.Question;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("production")
public interface QuestionRepo extends JpaRepository<Question, Long> {
}
