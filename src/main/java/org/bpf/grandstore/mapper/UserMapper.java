package org.bpf.grandstore.mapper;

import org.bpf.grandstore.dto.UserDto;
import org.bpf.grandstore.dto.UserDtoRequest;
import org.bpf.grandstore.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")  // spring can create beans at run time
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDtoRequest request);
}
