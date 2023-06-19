package com.socialhub.tfg.domain.vo;

import lombok.Data;

@Data
public class ResponseVerifyEmail {

    private boolean isVerified;
    private String token;
}
