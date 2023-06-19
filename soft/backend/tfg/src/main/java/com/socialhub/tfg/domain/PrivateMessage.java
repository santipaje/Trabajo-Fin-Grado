package com.socialhub.tfg.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table
@Data
public class PrivateMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Integer idMessage;

    @Column(name = "text")
    private String text;

    @Column(name = "send_date")
    private Date sendDate;

    @ManyToOne
    @JoinColumn(name = "id_sender")
    private User userSender;

    @ManyToOne
    @JoinColumn(name = "id_receiver")
    private User userReceiver;

}
