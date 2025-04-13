package com.ducle.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.ducle.user_service.model.dto.UserDTO;
import com.ducle.user_service.model.entity.User;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {
    
    UserDTO userToUserDTO(User user);
    
    User userDTOToUser(UserDTO userDTO);
}
