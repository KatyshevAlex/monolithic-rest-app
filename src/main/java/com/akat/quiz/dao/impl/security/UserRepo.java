package com.akat.quiz.dao.impl.security;

import com.akat.quiz.annotations.LogExecutionTime;
import com.akat.quiz.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepo  extends JpaRepository<User, Long> {

    @LogExecutionTime
    @Query(value = "SELECT * FROM security.users u WHERE u.email = :email ORDER BY id ASC LIMIT 1",
            nativeQuery = true)
    User findByEmail(@Param("email") String email);
}
