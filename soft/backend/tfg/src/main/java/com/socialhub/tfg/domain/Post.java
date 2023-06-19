package com.socialhub.tfg.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tfg_post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
    private Integer idPost;

    @Column(name = "header")
    private String header;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "post_content")
    private byte[] postContent;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> postComments;

    @OneToMany(mappedBy = "likedPost")
    private List<Likes> likes = new ArrayList<>();

}
