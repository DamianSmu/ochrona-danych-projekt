package com.example.odprojekt.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
public class BlockedToken {
    @Id
    private String id = UUID.randomUUID().toString();
    private String username;
    private Date date;

    public BlockedToken(String username) {
        this.username = username;
        date = new Date(new Date().getTime());
    }

    public BlockedToken() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return username;
    }

    public void setUser_id(String user_id) {
        this.username = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
