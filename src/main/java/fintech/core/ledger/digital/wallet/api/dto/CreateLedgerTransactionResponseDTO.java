package fintech.core.ledger.digital.wallet.api.dto;

import fintech.core.ledger.digital.wallet.api.entity.LedgerTransactionStatus;
import fintech.core.ledger.digital.wallet.api.entity.WalletAccount;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLedgerTransactionResponseDTO {


    private UUID sourceWalletAccountId;
    private UUID sourceWalletDestinationAccountId;
    private BigDecimal amount;

    private String currency;
    private LedgerTransactionStatus status;
    private String referenceId;
    private LocalDateTime createdAt;

}
