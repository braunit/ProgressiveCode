package com.progressive.code.crud.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by abraun on 23/11/2017.
 */
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Size(min=5, max=100)
    private String title;
    
    @Column(length=10000)
    @NotNull
    @Size(min=8, max=10000, message="Error: minimum size is 8 and maximum is 10000")
    private String content;

    public Notes() {
    }

    public Notes(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
