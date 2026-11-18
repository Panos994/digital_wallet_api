package fintech.core.ledger.digital.wallet.api.repository;

import fintech.core.ledger.digital.wallet.api.entity.TransactionEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionEntryRepository extends JpaRepository<TransactionEntry, UUID> {
}
