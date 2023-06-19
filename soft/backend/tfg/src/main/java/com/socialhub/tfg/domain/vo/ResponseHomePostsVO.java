package com.socialhub.tfg.domain.vo;

import com.socialhub.tfg.domain.Post;
import lombok.Data;

import java.util.List;

@Data
public class ResponseHomePostsVO {

    private Integer idUser;
    private List<Post> posts;
    private boolean isEmpty;

}
