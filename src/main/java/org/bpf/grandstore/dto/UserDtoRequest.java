package org.bpf.grandstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDtoRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "LastName is required")
    @Size(max = 255, message = "LastName must be less than 255 characters")
    private String lastName;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 25, message = "password must be between 6 to 25 characters")
    private String password;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;
}
