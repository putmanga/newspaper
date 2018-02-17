package com.company.newspaper.model.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSession {
    private String sessionId;
    private User user;
    private Boolean isValid;

}
