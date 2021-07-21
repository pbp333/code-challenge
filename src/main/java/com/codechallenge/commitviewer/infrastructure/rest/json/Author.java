package com.codechallenge.commitviewer.infrastructure.rest.json;

import java.time.Instant;

public class Author {

    private String name;
    private String email;
    private Instant date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

}
