package com.akat.quiz.services.impl;

import com.akat.quiz.dao.impl.security.ldap.LdapUserRepo;
import com.akat.quiz.model.ldap.LdapUser;
import com.akat.quiz.services.interfaces.ILdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LdapService implements ILdapService {
    @Autowired
    private LdapUserRepo ldapUserRepo;

    public Boolean authenticate(String u, String p) {
        return ldapUserRepo.findByUsernameAndPassword(u, p) != null;
    }

    public LdapUser create(String username, String password) {
        LdapUser newUser = new LdapUser(username,digestSHA(password));
        newUser.setId(LdapUtils.emptyLdapName());
        return ldapUserRepo.save(newUser);
    }

    public void modify(String u, String p) {
        LdapUser user = ldapUserRepo.findByUsername(u);
        user.setPassword(p);
        ldapUserRepo.save(user);
    }

    public List<String> search(String u) {
        List<LdapUser> userList = ldapUserRepo.findByUsernameLikeIgnoreCase(u);

        if (userList == null) {
            return Collections.emptyList();
        }

        return userList.stream()
                .map(LdapUser::getUsername)
                .collect(Collectors.toList());
    }




    private String digestSHA(final String password) {
        String base64;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            base64 = Base64.getEncoder()
                    .encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "{SHA}" + base64;
    }
}
