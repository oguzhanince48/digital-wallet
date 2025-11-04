package ing.Digital.Wallet.approval.service;

import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.BalanceChange;
import ing.Digital.Wallet.wallet_tx.jpa.WalletTxJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletTxApprovalService {
    private final WalletTxJpaRepositoryAdapter walletTxJpaRepositoryAdapter;
    private final WalletTxApprovalValidator walletTxApprovalValidator;
    private final WalletJpaRepositoryAdapter walletJpaRepositoryAdapter;

    @Transactional
    public WalletTx processApproval(Long walletTxId, OppositePartyStatus oppositePartyStatus) {
        WalletTx walletTx = walletTxJpaRepositoryAdapter.retrieve(walletTxId);
        walletTxApprovalValidator.validate(walletTx, oppositePartyStatus);
        walletTx = walletTxJpaRepositoryAdapter.updateStatus(walletTx);
        BalanceChange balanceChange = createBalanceChange(walletTx, oppositePartyStatus);
        walletJpaRepositoryAdapter.upsertBalance(balanceChange);
        return walletTx;
    }

    private BalanceChange createBalanceChange(WalletTx walletTx, OppositePartyStatus oppositePartyStatus) {
        BigDecimal usableBalanceAmount = OppositePartyStatus.APPROVED.equals(oppositePartyStatus) ? walletTx.getAmount() : BigDecimal.ZERO;
        BigDecimal amount = OppositePartyStatus.APPROVED.equals(oppositePartyStatus) ? BigDecimal.ZERO : walletTx.getAmount().negate();
        return BalanceChange.builder()
                .walletId(walletTx.getWalletId())
                .amount(walletTx.getAmount())
                .usableBalanceAmount(usableBalanceAmount)
                .build();
    }
}
