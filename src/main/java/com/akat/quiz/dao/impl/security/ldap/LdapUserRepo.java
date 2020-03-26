package com.akat.quiz.dao.impl.security.ldap;

import com.akat.quiz.model.ldap.LdapUser;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LdapUserRepo extends LdapRepository<LdapUser> {

    /**
     * Query Builder Mechanism - creating query by parsing method-name
     * https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html#repositories.query-methods.query-creation
     * */
    LdapUser findByUsername(String username);
    LdapUser findByUsernameAndPassword(String username, String password);
    List<LdapUser> findByUsernameLikeIgnoreCase(String username);
}
