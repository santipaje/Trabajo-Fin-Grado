package com.socialhub.tfg.domain.dto;

import com.socialhub.tfg.domain.Likes;
import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private String username;
    private Integer idPost;
    private String postContent;
    private String text;
    private Integer nLikes;
    private Boolean likeImage;
}
