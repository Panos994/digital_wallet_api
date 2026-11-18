package fintech.core.ledger.digital.wallet.api.dto;

import fintech.core.ledger.digital.wallet.api.entity.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponseDTO {
    private UUID userId;
    private String email;
    private Role role;
}
