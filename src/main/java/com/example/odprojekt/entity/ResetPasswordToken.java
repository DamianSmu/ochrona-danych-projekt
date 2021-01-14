package com.example.odprojekt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
public class ResetPasswordToken {

    @Id
    private String id = UUID.randomUUID().toString();
    @NotNull
    private String user_id;
    private Date expDate;

    private static final String FRONTEND_URL = "https://od-damian-smugorzewski-client.herokuapp.com";

    public ResetPasswordToken(@NotNull String user_id) {
        this.user_id = user_id;
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, 15);
        expDate = new Date(cal.getTime().getTime());
    }

    public ResetPasswordToken() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String generateLink(){
        return FRONTEND_URL + "/resetPassword?token=" + this.id;
    }
}
