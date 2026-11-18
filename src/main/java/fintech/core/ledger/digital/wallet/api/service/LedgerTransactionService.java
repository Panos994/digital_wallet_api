package fintech.core.ledger.digital.wallet.api.service;

import fintech.core.ledger.digital.wallet.api.dto.CreateLedgerTransactionRequestDTO;
import fintech.core.ledger.digital.wallet.api.dto.CreateLedgerTransactionResponseDTO;
import fintech.core.ledger.digital.wallet.api.entity.*;
import fintech.core.ledger.digital.wallet.api.repository.LedgerTransactionRepository;
import fintech.core.ledger.digital.wallet.api.repository.WalletAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LedgerTransactionService {
    private final LedgerTransactionRepository ledgerTransactionRepository;
    private final WalletAccountRepository walletAccountRepository;

    public LedgerTransactionService(LedgerTransactionRepository ledgerTransactionRepository, WalletAccountRepository walletAccountRepository) {
        this.ledgerTransactionRepository = ledgerTransactionRepository;
        this.walletAccountRepository = walletAccountRepository;
    }

    @Transactional
    public CreateLedgerTransactionResponseDTO travelFunds(CreateLedgerTransactionRequestDTO dto) {
        WalletAccount sourceAccount = walletAccountRepository.findByIdWithLock(dto.getSourceWalletAccountId()).orElseThrow(() -> new IllegalArgumentException("source account id does not exist"));
        WalletAccount destinationAccount = walletAccountRepository.findByIdWithLock(dto.getDestinationAccountId()).orElseThrow(() -> new IllegalArgumentException("destination account id does not exist"));
        if (sourceAccount.getStatus() != WalletAccountStatus.ACTIVE || destinationAccount.getStatus() != WalletAccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Account should be active!");
        }
        if (sourceAccount.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance to travel funds.");
        }
        if (!sourceAccount.getCurrency().equals(destinationAccount.getCurrency())) {
            throw new IllegalArgumentException("Currency should be the same for source and destination account to travel funds.");
        }
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(dto.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(dto.getAmount()));
        LedgerTransaction ledgerTransaction = new LedgerTransaction();
        ledgerTransaction.setSourceAccount(sourceAccount);
        ledgerTransaction.setDestinationAccount(destinationAccount);
        ledgerTransaction.setAmount(dto.getAmount());
        ledgerTransaction.setCurrency(sourceAccount.getCurrency());
        ledgerTransaction.setStatus(LedgerTransactionStatus.COMPLETED);
        ledgerTransaction.setReferenceId(UUID.randomUUID().toString());

        TransactionEntry debitTransaction = new TransactionEntry();
        debitTransaction.setLedgerTransaction(ledgerTransaction);
        debitTransaction.setAccount(sourceAccount);
        debitTransaction.setAmount(dto.getAmount());
        debitTransaction.setType(TransactionEntryType.DEBIT);

        TransactionEntry creditTransaction = new TransactionEntry();
        creditTransaction.setLedgerTransaction(ledgerTransaction);
        creditTransaction.setAccount(destinationAccount);
        creditTransaction.setAmount(dto.getAmount());
        creditTransaction.setType(TransactionEntryType.CREDIT);


        ledgerTransaction.setTransactionEntries(List.of(debitTransaction, creditTransaction));
        ledgerTransactionRepository.save(ledgerTransaction);

        return CreateLedgerTransactionResponseDTO.builder()
                .sourceWalletAccountId(sourceAccount.getId())
                .sourceWalletDestinationAccountId(destinationAccount.getId())
                .amount(ledgerTransaction.getAmount())
                .currency(ledgerTransaction.getCurrency())
                .status(ledgerTransaction.getStatus())
                .referenceId(ledgerTransaction.getReferenceId())
                .createdAt(ledgerTransaction.getCreatedAt())
                .build();
    }
}
