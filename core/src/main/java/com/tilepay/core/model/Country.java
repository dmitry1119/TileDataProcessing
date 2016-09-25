package com.tilepay.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Country {

    @Id
    private Long id;

    private String name;

    public Country() {
    }

    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
