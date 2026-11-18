package fintech.core.ledger.digital.wallet.api.controller;

import fintech.core.ledger.digital.wallet.api.dto.CreateUserRequestDTO;
import fintech.core.ledger.digital.wallet.api.dto.CreateUserResponseDTO;
import fintech.core.ledger.digital.wallet.api.entity.Role;
import fintech.core.ledger.digital.wallet.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping
    public ResponseEntity<CreateUserResponseDTO> getUserByEmail(@RequestParam String email){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email));
    }

    @PatchMapping("{userId}")
    public ResponseEntity<Void> assignRole(@PathVariable UUID userId, @RequestParam Role newRole){
        userService.assignRole(userId, newRole);
        return ResponseEntity.ok().build();
    }
}
