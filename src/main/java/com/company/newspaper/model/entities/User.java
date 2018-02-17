package com.company.newspaper.model.entities;

import com.company.newspaper.model.enums.Role;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<Role> roles;
}
