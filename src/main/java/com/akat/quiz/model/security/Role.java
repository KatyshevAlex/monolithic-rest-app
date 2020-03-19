package com.akat.quiz.model.security;

import com.akat.quiz.model.security.enums.RoleType;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "roles", schema = "security")
public class Role {

    public Role(RoleType rt){
        this.roleType = rt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private RoleType roleType;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            schema = "security",
            name = "roles_privileges",
            joinColumns = @JoinColumn( name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn( name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;
}
