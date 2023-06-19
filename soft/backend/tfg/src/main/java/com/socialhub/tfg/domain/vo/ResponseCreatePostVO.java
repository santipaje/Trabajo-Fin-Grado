package com.socialhub.tfg.domain.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class ResponseCreatePostVO {

    private Integer idPost;
    private Integer idUser;
    private String text;
    private Date creationDate;
    private byte[] postContent;

}
