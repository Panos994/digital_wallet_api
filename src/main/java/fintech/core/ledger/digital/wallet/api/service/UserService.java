package fintech.core.ledger.digital.wallet.api.service;

import fintech.core.ledger.digital.wallet.api.dto.CreateUserRequestDTO;
import fintech.core.ledger.digital.wallet.api.dto.CreateUserResponseDTO;
import fintech.core.ledger.digital.wallet.api.entity.Role;
import fintech.core.ledger.digital.wallet.api.entity.User;
import fintech.core.ledger.digital.wallet.api.repository.UserRepository;
import jakarta.persistence.RollbackException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateUserResponseDTO createUser(CreateUserRequestDTO dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("A user exists with the same email: ");
        }
        User user = new User();

        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);
        return CreateUserResponseDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public CreateUserResponseDTO getUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow((() -> new IllegalArgumentException("email already exists for this user")));
        return CreateUserResponseDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public void assignRole(UUID userId, Role newRole){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("User does not exist with this id: "+ userId));
        user.setRole(newRole);
    }
}
