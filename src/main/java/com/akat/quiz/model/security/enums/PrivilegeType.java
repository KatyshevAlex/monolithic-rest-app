package com.akat.quiz.model.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@ToString(of = {"action"})
public enum PrivilegeType {
    READ ("READ"),
    WRITE("WRITE");

    private final String action;

    public String[] getAllPriv(){
        return Arrays.stream(PrivilegeType.values()).map(PrivilegeType::getAction).toArray(String[]::new);
    }
}