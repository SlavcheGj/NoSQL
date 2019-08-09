package com.endava.NoSQL.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("Product")
public class Product implements Serializable{

    @Id
    private Long id;
    private String category;
    private String subcategory;
    private String name;
    private String description;
    private List<String> tags;

    public Product() {
    }

    public Product(Long id, String category, String subcategory, String name, String description, List<String> tags) {
        this.id = id;
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }
}
