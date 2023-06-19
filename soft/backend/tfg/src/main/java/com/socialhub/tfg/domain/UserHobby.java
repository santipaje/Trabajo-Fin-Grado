package com.socialhub.tfg.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tfg_user_hobby")
@Data
public class UserHobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_hobby")
    private Integer idUserHobby;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name= "id_hobby")
    private Hobby hobby;

}
