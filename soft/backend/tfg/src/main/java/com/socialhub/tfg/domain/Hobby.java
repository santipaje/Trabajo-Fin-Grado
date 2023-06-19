package com.socialhub.tfg.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "tfg_hobby")
@Data
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hobby")
    private Integer idHobby;

    @Column(name = "hobby_name")
    private String hobby_name;

    @Column(name = "hobby_description")
    private String description;

}
