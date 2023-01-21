package org.elsys.diplom.service.mapper;

import org.elsys.diplom.entity.User;
import org.elsys.diplom.service.dto.UserRegisterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {User.class, UserRegisterDTO.class})
public abstract class UserMapper {
    abstract public User toEntity(UserRegisterDTO userRegisterDTO);
    abstract public UserRegisterDTO toDto(User user);
}
