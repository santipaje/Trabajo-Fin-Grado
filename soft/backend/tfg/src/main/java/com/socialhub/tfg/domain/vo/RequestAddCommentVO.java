package com.socialhub.tfg.domain.vo;

import lombok.Data;

@Data
public class RequestAddCommentVO {

    private Integer idUserPost;
    private Integer idPost;
    private String text;

}
