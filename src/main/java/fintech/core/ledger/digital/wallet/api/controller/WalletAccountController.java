package fintech.core.ledger.digital.wallet.api.controller;

import fintech.core.ledger.digital.wallet.api.dto.CreateWalletAccountResponseDTO;
import fintech.core.ledger.digital.wallet.api.dto.CreateWalletAccountDTO;
import fintech.core.ledger.digital.wallet.api.service.WalletAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallet-accounts")
public class WalletAccountController {
    private final WalletAccountService walletAccoutService;

    public WalletAccountController(WalletAccountService walletAccoutService) {
        this.walletAccoutService = walletAccoutService;
    }

    @PostMapping
    public ResponseEntity<CreateWalletAccountResponseDTO> createAccount(@RequestBody CreateWalletAccountDTO dto, @RequestParam UUID userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(walletAccoutService.createAccount(dto,userId));
    }
}
