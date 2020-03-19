package com.akat.quiz.dao.impl.quiz;

import com.akat.quiz.model.entities.Quiz;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("production")
public interface QuizRepo extends JpaRepository<Quiz, Long> {
}
