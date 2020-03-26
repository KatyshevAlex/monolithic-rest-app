package com.akat.quiz.services.interfaces;

import com.akat.quiz.exceptions.EmailExistsException;
import com.akat.quiz.model.security.User;
import com.akat.quiz.model.security.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ISecurityService {

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;
}
