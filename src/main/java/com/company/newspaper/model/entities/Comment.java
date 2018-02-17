package com.company.newspaper.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    private Integer id;
    private String bode;
    private Date createdOn;
    private User createdBy;
}
