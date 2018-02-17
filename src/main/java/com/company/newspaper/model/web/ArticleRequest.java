package com.company.newspaper.model.web;

import com.company.newspaper.model.entities.Comment;
import com.company.newspaper.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleRequest {
    private String title;
    private String body;
    private List<Category> categories;
    private List<Comment> comments;
}
