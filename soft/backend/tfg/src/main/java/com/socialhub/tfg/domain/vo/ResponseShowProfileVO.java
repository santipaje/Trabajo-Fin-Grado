package com.socialhub.tfg.domain.vo;

import com.socialhub.tfg.domain.Post;
import com.socialhub.tfg.domain.dto.PostDTO;
import lombok.Data;
import org.springframework.boot.SpringApplicationRunListener;

import java.util.List;

@Data
public class ResponseShowProfileVO {

    private String username;
    private Integer nFollowers;
    private Integer nFollowing;
    private Integer nPosts;
    private String profilePic;

    private List<PostDTO> posts;

    private List<String> followRequests;

}
