package com.company.newspaper.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Role {
    READER(1, "ROLE_READER"),
    WRITER(2, "ROLE_WRITER"),
    ADMIN(3, "ROLE_ADMIN");

    private Integer index;
    private String roleName;

    public static Role getByIndex(Integer index) {
        Role[] roles = Role.values();
        for (Role role : roles) {
            if (role.getIndex().equals(index)) {
                return role;
            }
        }

        return null;
    }


    public List<Role> getIncludedRoles() {
        return Arrays.stream(Role.values())
                .filter(r -> r.getIndex() <= getIndex())
                .collect(Collectors.toList());


    }
}
