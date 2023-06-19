package com.socialhub.tfg.domain.vo;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ResponseSearchUserVO {

    private String username;
    private Integer nFollowers;
    private Integer nFollowing;
    private Integer nPosts;
    private String profilePic;
    private String requestStatus;

}
