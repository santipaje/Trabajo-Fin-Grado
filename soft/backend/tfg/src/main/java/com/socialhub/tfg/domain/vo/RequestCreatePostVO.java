package com.socialhub.tfg.domain.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class RequestCreatePostVO {

    private String header;
    private MultipartFile postContent;

}
