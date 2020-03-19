package com.akat.quiz.model.security;

import com.akat.quiz.model.security.enums.PrivilegeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "privileges", schema = "security")
@SequenceGenerator(name = "sq_privileges", sequenceName = "sq_privileges", allocationSize = 1, schema = "security")
public class Privilege {

    public Privilege(PrivilegeType pt){
        this.privilegeType = pt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_privileges")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PrivilegeType privilegeType;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}