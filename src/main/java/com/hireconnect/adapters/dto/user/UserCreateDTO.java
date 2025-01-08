package com.hireconnect.adapters.dto.user;

import com.hireconnect.core.annotation.optionalNotBlank.OptionalNotBlank;
import com.hireconnect.core.annotation.validEnum.ValidEnum;
import com.hireconnect.core.entity.TypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    @Size(max = 50, message = "the size must be less than 50 characters.")
    @NotBlank(message = "name is required.")
    private String name;

    @Email(message = "must be an email.")
    @NotBlank(message = "email is required.")
    @Size(max = 50, message = "the size must be less than 50 characters.")
    private String email;

    @NotBlank(message = "password is required.")
    private String password;

    @Size(max = 2000, message = "the size must be less than 2000 characters.")
    @OptionalNotBlank(message = "imgUrl must not be blank.")
    private String imgUrl;

    @NotNull(message = "typeUser is required.")
    @ValidEnum(enumClass = TypeUser.class, message = "Invalid value for typeUser. Accepted values are: FREELANCER, ADMIN, COMPANY.")
    private String typeUser;
}
