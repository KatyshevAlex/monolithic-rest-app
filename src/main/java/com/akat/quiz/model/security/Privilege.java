package com.akat.quiz.model.security;

import com.akat.quiz.model.security.enums.PrivilegeType;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "privileges", schema = "security")
public class Privilege {

    public Privilege(PrivilegeType pt){
        this.privilegeType = pt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private PrivilegeType privilegeType;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}