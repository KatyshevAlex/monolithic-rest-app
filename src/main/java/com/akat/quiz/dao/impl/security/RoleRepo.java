package com.akat.quiz.dao.impl.security;

import com.akat.quiz.annotations.LogExecutionTime;
import com.akat.quiz.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepo  extends JpaRepository<Role,Long> {

    @LogExecutionTime
    @Query(value = "SELECT * FROM roles a WHERE a.role_type = :role_type ORDER BY id ASC LIMIT 1",
            nativeQuery = true)
    Role findByRoleType(@Param("role_type") String roleTypeString);

}
