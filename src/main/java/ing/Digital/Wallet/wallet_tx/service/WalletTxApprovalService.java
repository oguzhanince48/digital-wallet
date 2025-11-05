package ing.Digital.Wallet.wallet_tx.service;

import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.BalanceChange;
import ing.Digital.Wallet.wallet_tx.jpa.WalletTxJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxApproval;
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
    private final WalletJpaRepositoryAdapter walletJpaRepositoryAdapter;

    @Transactional
    public WalletTx processApproval(WalletTxApproval walletTxApproval) {
        WalletTx walletTx = walletTxJpaRepositoryAdapter.retrieve(walletTxApproval.getTransactionId());
        BalanceChange balanceChange = createBalanceChange(walletTx, walletTxApproval);
        walletJpaRepositoryAdapter.upsertBalance(balanceChange);
        return walletTxJpaRepositoryAdapter.updateStatus(walletTx,walletTxApproval);
    }

    private BalanceChange createBalanceChange(WalletTx walletTx, WalletTxApproval walletTxApproval) {
        return switch (walletTx.getTransactionType()) {
            case DEPOSIT -> buildDepositBalanceChange(walletTx, walletTxApproval);
            case WITHDRAW -> buildWithdrawBalanceChange(walletTx, walletTxApproval);
            default ->
                    throw new IllegalArgumentException("Invalid opposite party status: " + walletTxApproval.getOppositePartyStatus());
        };
    }

    private BalanceChange buildDepositBalanceChange(WalletTx walletTx, WalletTxApproval walletTxApproval){
        if (OppositePartyStatus.APPROVED.equals(walletTxApproval.getOppositePartyStatus())) {
            return BalanceChange.builder()
                    .walletId(walletTx.getWalletId())
                    .amount(BigDecimal.ZERO)
                    .usableBalanceAmount(walletTx.getAmount())
                    .customerId(walletTxApproval.getCustomerId())
                    .build();
        } else {
            return BalanceChange.builder()
                    .walletId(walletTx.getWalletId())
                    .amount(walletTx.getAmount().negate())
                    .usableBalanceAmount(BigDecimal.ZERO)
                    .customerId(walletTxApproval.getCustomerId())
                    .build();
        }
    }

    private BalanceChange buildWithdrawBalanceChange(WalletTx walletTx, WalletTxApproval walletTxApproval){
        if (OppositePartyStatus.APPROVED.equals(walletTxApproval.getOppositePartyStatus())) {
            return BalanceChange.builder()
                    .walletId(walletTx.getWalletId())
                    .amount(walletTx.getAmount().negate())
                    .usableBalanceAmount(BigDecimal.ZERO)
                    .customerId(walletTxApproval.getCustomerId())
                    .build();
        } else {
            return BalanceChange.builder()
                    .walletId(walletTx.getWalletId())
                    .amount(BigDecimal.ZERO)
                    .usableBalanceAmount(walletTx.getAmount())
                    .customerId(walletTxApproval.getCustomerId())
                    .build();
        }
    }
}
