package fintech.core.ledger.digital.wallet.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="wallet_accounts")
public class WalletAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private User user;
    @Column
    private BigDecimal balance;

    @Column
    private String currency;

    @OneToMany(mappedBy = "sourceAccount",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LedgerTransaction> sourceTransactions;

    @OneToMany(mappedBy = "destinationAccount",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LedgerTransaction> destinationTransactions;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionEntry> transactionEntries;
    @Column
    @Enumerated(EnumType.STRING)
    private WalletAccountStatus status;
}
