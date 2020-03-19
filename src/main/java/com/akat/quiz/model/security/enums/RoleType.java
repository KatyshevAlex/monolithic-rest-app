package com.akat.quiz.model.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@ToString(of = {"role"})
public enum RoleType {
    USER ("USER", 1),
    PREMIUM_USER("PREMIUM_USER", 2),
    ADMIN("ADMIN", 3),

    NO_ROLE("NO_ROLE", 0);

    private final String role;
    private final int priority;

    public String[] getThisAndHigherPriorities(){
        return Arrays.stream(RoleType.values()).filter(a-> a.getPriority() >= this.priority).map(RoleType::getRole).toArray(String[]::new);
    }

    public String[] getAllRoles(){
        return Arrays.stream(RoleType.values()).map(RoleType::getRole).toArray(String[]::new);
    }

}
