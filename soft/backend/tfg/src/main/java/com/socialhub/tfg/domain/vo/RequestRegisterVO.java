package com.socialhub.tfg.domain.vo;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;

@Data
public class RequestRegisterVO {

    private String username;
    private String fullname;
    private String password;
    private Date birthDate;
    private String email;
    private String phone;
    private Date registrationDate;
    private byte[] profilePic;
    private boolean isPrivate;
    private boolean isEnabled;

}
