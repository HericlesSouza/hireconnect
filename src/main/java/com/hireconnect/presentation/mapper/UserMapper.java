package com.hireconnect.presentation.mapper;

import com.hireconnect.core.entity.User;
import com.hireconnect.presentation.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDTO toUserDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    public <T> User toUserEntity(T userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}
