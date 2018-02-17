package com.company.newspaper.model.entities;

import com.company.newspaper.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article {
    private Integer id;
    private String title;
    private String body;
    private Date createdOn;
    private User createdBy;
    private List<Category> categories;
    private List<Comment> comments;
}
