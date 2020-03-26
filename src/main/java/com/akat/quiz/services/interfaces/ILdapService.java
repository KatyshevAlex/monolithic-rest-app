package com.akat.quiz.services.interfaces;

import com.akat.quiz.annotations.LogExecutionTime;

import java.util.List;

public interface ILdapService {
    @LogExecutionTime
    Boolean authenticate(String u, String p);

    void create(String username, String password);

    void modify(String u, String p);

    List<String> search(String u);
}
