package fintech.core.ledger.digital.wallet.api.dto;

import fintech.core.ledger.digital.wallet.api.entity.TransactionEntryType;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionEntryResponseDTO {

    private UUID ledgerTransactionId;
    private UUID walletAccountId;
    private BigDecimal amount;
    private TransactionEntryType type;





}
