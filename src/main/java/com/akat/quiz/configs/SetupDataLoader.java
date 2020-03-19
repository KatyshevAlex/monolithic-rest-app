package com.akat.quiz.configs;

import com.akat.quiz.dao.impl.security.PrivilegeRepo;
import com.akat.quiz.dao.impl.security.RoleRepo;
import com.akat.quiz.dao.impl.security.UserRepo;
import com.akat.quiz.model.security.Privilege;
import com.akat.quiz.model.security.Role;
import com.akat.quiz.model.security.User;
import com.akat.quiz.model.security.enums.PrivilegeType;
import com.akat.quiz.model.security.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PrivilegeRepo privilegeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound(PrivilegeType.READ);
        Privilege writePrivilege = createPrivilegeIfNotFound(PrivilegeType.WRITE);

        createRoleIfNotFound(RoleType.ADMIN, Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound(RoleType.USER, Arrays.asList(readPrivilege));

        Role adminRole = roleRepo.findByRoleType(RoleType.ADMIN.toString());
        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepo.save(user);

        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(PrivilegeType pt) {

        Privilege privilege = privilegeRepo.findByType(pt.toString());
        if (privilege == null) {
            privilege = new Privilege(pt);
            privilegeRepo.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(RoleType roleType, Collection<Privilege> privileges) {

        Role role = roleRepo.findByRoleType(roleType.toString());
        if (role == null) {
            role = new Role(roleType);
            role.setPrivileges(privileges);
            roleRepo.save(role);
        }
        return role;
    }
}
