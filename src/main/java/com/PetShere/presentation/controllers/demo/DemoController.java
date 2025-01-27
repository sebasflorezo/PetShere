package com.PetShere.presentation.controllers.demo;

import com.PetShere.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping
    public String welcome() {
        return "Authentication works!";
    }

    // NOTE: Use PreAuthorize for each method that needs authorization
    @GetMapping("/admin")
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public String testOnlyAdminRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roles = authentication.getAuthorities().toString();
        log.warn("Rol: " + roles);
        return "Admin authorized";
    }
}
