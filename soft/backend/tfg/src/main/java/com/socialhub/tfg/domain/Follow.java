package com.socialhub.tfg.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "tfg_follow")
@Data
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_follow")
    private Integer idFollow;

    // persona que sigue
    @ManyToOne
    @JoinColumn(name = "id_follower")
    private User follower;

    // persona seguida por la que sigue
    @ManyToOne
    @JoinColumn(name= "id_following")
    private User following;

    @Column(name = "follow_date")
    @NotNull
    private Date followDate;

}
