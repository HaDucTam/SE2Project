package com.example.se2project.entity.dto;

import com.example.se2project.entity.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {

    @Email
    private String email;

    @NotBlank
    @Length(min = 5, max = 30)
    private String password;

    @NotBlank
    @Length(max = 30)
    private String firstName;

    @NotBlank
    @Length(max = 30)
    private String lastName;

    private Role role;
}
