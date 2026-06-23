package org.bpf.grandstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {

    private Long id;

    private String name;

    private String lastName;

    private String email;
}
