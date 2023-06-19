package com.socialhub.tfg.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tfg_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "username")
    @NotBlank
    private String username;

    @Column(name = "fullname")
    @NotBlank
    private String fullname;

    @Column(name = "passwordd")
    @NotBlank
    private String password;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "profile_pic")
    private byte[] profilePic;

    @Column(name = "private")
    @NotNull
    private boolean isPrivate;

    @Column(name = "enabled")
    @NotNull
    private boolean isEnabled;

    @OneToMany(mappedBy = "user")
    private List<Post> postedByUser;

    @OneToMany(mappedBy = "follower")
    private List<Follow> followers;

    @OneToMany(mappedBy = "following")
    private List<Follow> following;

    @OneToMany(mappedBy = "userRequester")
    private List<FriendRequest> friendRequestsMade;

    @OneToMany(mappedBy = "userReceiver")
    private List<FriendRequest> FriendRequestsReceived;

    @OneToMany(mappedBy = "likingUser")
    private List<Likes> likes = new ArrayList<>();
}
