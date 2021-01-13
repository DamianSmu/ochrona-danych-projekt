package com.example.odprojekt.entity;


import com.example.odprojekt.security.RoleEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
public class User implements Serializable {

    @Id
    private String id = UUID.randomUUID().toString();
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    @JsonIgnore
    private String password;

    @ElementCollection(targetClass = RoleEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles")
    @Column(name = "role")
    private Set<RoleEnum> roles = new HashSet<>();

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Note> notes = new ArrayList<>();
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PublicNote> publicNotes = new ArrayList<>();


    public User(@NotNull String username, @NotNull String email, @NotNull String password, Set<RoleEnum> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<PublicNote> getPublicNotes() {
        return publicNotes;
    }

    public void setPublicNotes(List<PublicNote> publicNotes) {
        this.publicNotes = publicNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, roles);
    }
}