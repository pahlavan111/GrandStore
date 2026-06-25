package org.bpf.grandstore.mapper;

import org.bpf.grandstore.dto.UpdateUserRequest;
import org.bpf.grandstore.dto.UserDto;
import org.bpf.grandstore.dto.UserDtoRequest;
import org.bpf.grandstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")  // spring can create beans at run time
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDtoRequest request);

    void update(UpdateUserRequest request, @MappingTarget User user);
}
