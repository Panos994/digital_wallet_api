package fintech.core.ledger.digital.wallet.api.dto;

import fintech.core.ledger.digital.wallet.api.entity.LedgerTransaction;
import fintech.core.ledger.digital.wallet.api.entity.TransactionEntryType;
import fintech.core.ledger.digital.wallet.api.entity.WalletAccount;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionEntryRequestDTO {


    private UUID ledgerTransactionId;
    private UUID walletAccountId;



}
