package com.socialhub.tfg.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "tfg_likes")
@Data
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_like")
    private Integer idLike;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User likingUser;

    @ManyToOne
    @JoinColumn(name= "id_post")
    private Post likedPost;

    @Column(name = "creation_date")
    private Date creationDate;

}
