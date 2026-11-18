package fintech.core.ledger.digital.wallet.api.repository;

import fintech.core.ledger.digital.wallet.api.entity.WalletAccount;
import jakarta.persistence.LockModeType;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletAccountRepository extends JpaRepository<WalletAccount, UUID> {
    List<WalletAccount> findByUserId(UUID userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM WalletAccount w WHERE w.id = :id")
    Optional<WalletAccount> findByIdWithLock(@Param("id")UUID id);
}
