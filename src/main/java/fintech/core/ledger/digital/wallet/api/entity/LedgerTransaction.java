package fintech.core.ledger.digital.wallet.api.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ledger_transactions")
public class LedgerTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private WalletAccount sourceAccount;

    @ManyToOne
    private WalletAccount destinationAccount;

    @Column
    private BigDecimal amount;

    @Column
    private String currency;

    @Column
    @Enumerated(EnumType.STRING)
    private LedgerTransactionStatus status;

    @Column(unique = true)
    private String referenceId;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "ledgerTransaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionEntry> transactionEntries;



    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }


}
