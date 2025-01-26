package app.PetShere.controllers.admin;

import app.PetShere.models.user.Role;
import app.PetShere.services.admin.AdminServiceImpl;
import app.PetShere.utils.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;

    @GetMapping("/users")
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminServiceImpl.getUsers());
    }

    @GetMapping("/roles")
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> getUserRoles() {
        return ResponseEntity.ok(Role.values());
    }

    @PostMapping("/changeUserRole")
    @PreAuthorize(Constants.ADMIN_AUTHORITY)
    public ResponseEntity<?> changeUserRole(@RequestBody JsonNode body) {
        try {
            Long userId = body.get("userId").asLong();
            String role = body.get("role").asText();
            adminServiceImpl.changeUserRole(userId, role);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.BAD_REQUEST_MSG);
        }
    }
}
