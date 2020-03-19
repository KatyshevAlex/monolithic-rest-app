package com.akat.quiz.dao.impl.security;

import com.akat.quiz.annotations.LogExecutionTime;
import com.akat.quiz.model.security.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {

    @LogExecutionTime
    @Query(value = "SELECT * FROM privileges a WHERE a.action = :action ORDER BY id ASC LIMIT 1",
            nativeQuery = true)
    Privilege findByAction(@Param("action") String action);
}
