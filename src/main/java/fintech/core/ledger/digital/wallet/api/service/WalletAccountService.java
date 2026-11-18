package fintech.core.ledger.digital.wallet.api.service;

import fintech.core.ledger.digital.wallet.api.dto.CreateWalletAccountResponseDTO;
import fintech.core.ledger.digital.wallet.api.dto.CreateWalletAccountDTO;
import fintech.core.ledger.digital.wallet.api.entity.User;
import fintech.core.ledger.digital.wallet.api.entity.WalletAccount;
import fintech.core.ledger.digital.wallet.api.entity.WalletAccountStatus;
import fintech.core.ledger.digital.wallet.api.repository.UserRepository;
import fintech.core.ledger.digital.wallet.api.repository.WalletAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletAccountService {
    private final WalletAccountRepository walletAccountRepository;
    private final UserRepository userRepository;

    public WalletAccountService(WalletAccountRepository walletAccountRepository, UserRepository userRepository) {
        this.walletAccountRepository = walletAccountRepository;
        this.userRepository = userRepository;
    }

    public CreateWalletAccountResponseDTO createAccount(CreateWalletAccountDTO dto, UUID userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("User is not found with this userId: " + userId));
        WalletAccount walletAccount = new WalletAccount();
        walletAccount.setCurrency(dto.getCurrency());
        walletAccount.setUser(user);
        walletAccount.setBalance(BigDecimal.ZERO);
        walletAccount.setStatus(WalletAccountStatus.ACTIVE);

        walletAccountRepository.save(walletAccount);
        return CreateWalletAccountResponseDTO.builder()
                .userId(walletAccount.getUser().getId())
                .currency(walletAccount.getCurrency())
                .balance(walletAccount.getBalance())
                .build();

    }
}
