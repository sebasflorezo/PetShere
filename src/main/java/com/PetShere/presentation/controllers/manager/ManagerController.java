package com.PetShere.presentation.controllers.manager;

import com.PetShere.service.implementation.manager.ManagerServiceImpl;
import com.PetShere.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerServiceImpl managerServiceImpl;

    @GetMapping("/user-roles")
    @PreAuthorize(Constants.MANAGER_AUTHORITY)
    public ResponseEntity<?> getUserRoles() {
        return ResponseEntity.ok(managerServiceImpl.getUserRoles());
    }

    // TODO: implementar métodos para generar reportes, sea acá o en reports
}
