package com.akat.quiz.dao.impl.security;

import com.akat.quiz.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<User, Long> {
}
