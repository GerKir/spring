package com.gerkir.spring.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "news_id")
    private long id;

    private String title;
    private String text;

    @ElementCollection(targetClass = Categories.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "news_categories", joinColumns = @JoinColumn(name = "news_id"))
    @Enumerated(EnumType.STRING)
    private Set<Categories> categories;

    public News() {
    }

    public News(String title, String text, Set<Categories> categories) {
        this.title = title;
        this.text = text;
        this.categories = categories;
    }

    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
