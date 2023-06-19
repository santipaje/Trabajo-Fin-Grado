package com.socialhub.tfg.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "tfg_friend_request")
@Data
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Integer idRequest;

    @Column(name = "accepted")
    private boolean accepted;

    @Column(name = "request_date")
    private Date requestDate;

    @ManyToOne
    @JoinColumn(name = "id_requester")
    private User userRequester;

    @ManyToOne
    @JoinColumn(name = "id_receiver")
    private User userReceiver;

}
