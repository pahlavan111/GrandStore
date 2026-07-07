package org.bpf.grandstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.bpf.grandstore.validation.Lowercase;


@Data
public class LoginRequestBody {

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Lowercase
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 25, message = "password must be between 6 to 25 characters")
    private String password;

}
