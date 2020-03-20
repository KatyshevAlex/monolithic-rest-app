package com.akat.quiz.services.impl;

import com.akat.quiz.dao.impl.security.RoleRepo;
import com.akat.quiz.dao.impl.security.UserRepo;
import com.akat.quiz.exceptions.EmailExistsException;
import com.akat.quiz.model.security.Privilege;
import com.akat.quiz.model.security.Role;
import com.akat.quiz.model.security.User;
import com.akat.quiz.model.security.UserDto;
import com.akat.quiz.model.security.enums.PrivilegeType;
import com.akat.quiz.model.security.enums.RoleType;
import com.akat.quiz.services.interfaces.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService implements ISecurityService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {

        if (isEmailExists(accountDto.getEmail()))
            throw new EmailExistsException(accountDto.getEmail());

        User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());

        user.setRoles(Arrays.asList(roleRepo.findByRoleType(RoleType.USER.toString())));
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User user = userRepo.findByEmail(email);

            if (user == null) {
                return new org.springframework.security.core.userdetails.User(
                        " ",
                        " ",
                        true,
                        true,
                        true,
                        true,
                        getAuthorities(Arrays.asList(roleRepo.findByRoleType(RoleType.USER.toString()))));
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.isEnabled(),
                    true,
                    true,
                    true,
                    getAuthorities(user.getRoles()));
    }


    /**
     * 1. Take all roles of the user
     * 2. Take all privileges for each role
     * 3. Make List <GrantedAuthority> where GrantedAuthority == privilege
     * */
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<PrivilegeType> getPrivileges(Collection<Role> roles) {
        return roles.stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .map(Privilege::getPrivilegeType)
                .collect(Collectors.toList());
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<PrivilegeType> privileges) {
        return privileges.stream()
                .map((a) -> new SimpleGrantedAuthority(a.toString()))
                .collect(Collectors.toList());
    }

    private Boolean isEmailExists(String email){
        return userRepo.findByEmail(email) != null;
    }
}
