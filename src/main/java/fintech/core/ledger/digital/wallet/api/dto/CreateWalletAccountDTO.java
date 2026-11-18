package fintech.core.ledger.digital.wallet.api.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWalletAccountDTO {
    private UUID userId;
    private String currency;
}
