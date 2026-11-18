package fintech.core.ledger.digital.wallet.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="transaction_entries")
public class TransactionEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private LedgerTransaction ledgerTransaction;

    @ManyToOne
    private WalletAccount account;

    @Column
    private BigDecimal amount;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionEntryType type;
}
