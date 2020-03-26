package com.akat.quiz.model.ldap;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(
        base="ou=users",
        objectClasses = {"person", "inetOrgPerson", "top"})
@Data
@NoArgsConstructor
public class LdapUser {

    public LdapUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Id
    private Name id;
    private @Attribute(name = "cn") String username;
    private @Attribute(name = "sn") String password;
}
