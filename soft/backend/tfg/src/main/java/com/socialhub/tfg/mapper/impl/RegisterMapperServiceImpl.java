package com.socialhub.tfg.service.mapper.impl;
import com.socialhub.tfg.domain.User;
import com.socialhub.tfg.domain.vo.ResponseRegisterVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RegisterMapperServiceImpl {

    private static final ModelMapper modelMapper = new ModelMapper();

    public ResponseRegisterVO convertToVO (User user){
         return modelMapper.map(user, ResponseRegisterVO.class);
    }

    public User convertToEntity (ResponseRegisterVO vo){
        return modelMapper.map(vo, User.class);
    }

}
