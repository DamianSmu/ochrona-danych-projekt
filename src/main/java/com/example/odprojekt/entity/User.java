package com.example.odprojekt.entity;


import com.example.odprojekt.security.RoleEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
public class User implements Serializable {

    @Id
    private String id = UUID.randomUUID().toString();
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;

    @ElementCollection(targetClass = RoleEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles")
    @Column(name = "role")
    private Set<RoleEnum> roles = new HashSet<>();
}