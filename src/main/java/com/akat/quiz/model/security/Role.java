package com.akat.quiz.model.security;

import com.akat.quiz.model.security.enums.RoleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles", schema = "security")
@SequenceGenerator(name = "sq_roles", sequenceName = "sq_roles", allocationSize = 1, schema = "security")
public class Role {

    public Role(RoleType rt){
        this.roleType = rt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_roles")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
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
