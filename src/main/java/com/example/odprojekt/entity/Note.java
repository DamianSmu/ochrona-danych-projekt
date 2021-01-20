package com.example.odprojekt.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Note {

    @Id
    private String id = UUID.randomUUID().toString();
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    @NotNull
    private String body;

    public Note(@NotNull User user, @NotNull String body) {
        this.user = user;
        this.body = body;
    }

    public Note() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
