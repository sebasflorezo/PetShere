package com.PetShere.presentation.dto.user;

import com.PetShere.persistence.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesDto {
    private Long id;
    private String email;
    private String name;
    private Boolean state;
    private Role role;
}
