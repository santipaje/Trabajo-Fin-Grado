package com.socialhub.tfg.domain.vo;

import lombok.Data;
import org.springframework.http.HttpHeaders;

@Data
public class ResponseLoginVO {

    private HttpHeaders header;
    private Integer id_user;

}
