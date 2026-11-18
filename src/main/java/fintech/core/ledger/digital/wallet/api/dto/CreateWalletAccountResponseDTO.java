package fintech.core.ledger.digital.wallet.api.dto;

import fintech.core.ledger.digital.wallet.api.entity.WalletAccountStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWalletAccountResponseDTO {
    private UUID userId;
    private WalletAccountStatus status;
    private BigDecimal balance;
    private String currency;

}
