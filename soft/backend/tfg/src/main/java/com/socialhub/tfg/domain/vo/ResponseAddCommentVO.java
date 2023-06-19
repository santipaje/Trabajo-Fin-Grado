package com.socialhub.tfg.domain.vo;

import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Date;

@Data
public class ResponseAddCommentVO {

    private String text;
    private Date creationDate;
    private Integer idUserPost;
    private Integer idPost;

}
