package fintech.core.ledger.digital.wallet.api.controller;

import fintech.core.ledger.digital.wallet.api.dto.CreateLedgerTransactionRequestDTO;
import fintech.core.ledger.digital.wallet.api.dto.CreateLedgerTransactionResponseDTO;
import fintech.core.ledger.digital.wallet.api.service.LedgerTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ledger-transactions")
public class LedgerTransactionController {
    private final LedgerTransactionService ledgerTransactionService;

    public LedgerTransactionController(LedgerTransactionService ledgerTransactionService) {
        this.ledgerTransactionService = ledgerTransactionService;
    }
    @PostMapping
    public ResponseEntity<CreateLedgerTransactionResponseDTO> transferFunds(@RequestBody CreateLedgerTransactionRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ledgerTransactionService.travelFunds(dto));
    }
}
