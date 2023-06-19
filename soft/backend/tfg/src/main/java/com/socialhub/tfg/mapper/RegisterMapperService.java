package com.socialhub.tfg.service.mapper;

import com.socialhub.tfg.domain.User;
import com.socialhub.tfg.domain.vo.ResponseRegisterVO;

public interface RegisterMapperService {

    public ResponseRegisterVO convertToVO (User user);

    public User convertToEntity (ResponseRegisterVO vo);
}
