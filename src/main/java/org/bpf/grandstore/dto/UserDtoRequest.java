package org.bpf.grandstore.dto;

import lombok.Data;

@Data
public class UserDtoRequest {

    private String name;

    private String lastName;

    private String password;

    private String email;
}
