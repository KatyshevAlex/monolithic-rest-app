package com.akat.quiz.services.interfaces;

import com.akat.quiz.annotations.LogExecutionTime;
import com.akat.quiz.model.ldap.LdapUser;

import java.util.List;

public interface ILdapService {
    @LogExecutionTime
    Boolean authenticate(String u, String p);

    LdapUser create(String username, String password);

    void modify(String u, String p);

    List<String> search(String u);
}
