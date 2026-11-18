package fintech.core.ledger.digital.wallet.api.repository;

import fintech.core.ledger.digital.wallet.api.entity.LedgerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LedgerTransactionRepository extends JpaRepository<LedgerTransaction, UUID> {
    Optional<LedgerTransaction> findByReferenceId(String referenceId);
//    List<LedgerTransaction> findBySourceAccountOrDestinationAccountId(UUID sourceAccountId, UUID destinationAccountId);
    @Query("SELECT t FROM LedgerTransaction t where t.sourceAccount.id = :accountId OR t.destinationAccount.id = :accountId")
    List<LedgerTransaction> findByAccountInvolving(@Param("accountId") UUID accountId);
}
