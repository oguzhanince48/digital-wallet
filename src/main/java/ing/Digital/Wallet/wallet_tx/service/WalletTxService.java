package ing.Digital.Wallet.wallet_tx.service;

import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.BalanceChange;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import ing.Digital.Wallet.wallet_tx.jpa.WalletTxJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.WalletDeposit;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxInfo;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearch;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearchResult;
import ing.Digital.Wallet.wallet_tx.service.model.WalletWithdraw;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Service
public class WalletTxService {

    private final WalletTxJpaRepositoryAdapter walletTxJpaRepositoryAdapter;
    private final WalletJpaRepositoryAdapter walletJpaRepositoryAdapter;

    private static final BigDecimal TX_THRESHOLD = BigDecimal.valueOf(1000);

    @Transactional(readOnly = true)
    public WalletTxSearchResult search(WalletTxSearch walletTxSearch) {
        return walletTxJpaRepositoryAdapter.search(walletTxSearch);
    }

    @Transactional
    public WalletTx deposit(WalletDeposit walletDeposit){
        OppositePartyStatus oppositePartyStatus = decideOppositePartyStatus(walletDeposit.getAmount());
        BalanceChange balanceChange = BalanceChange.builder()
                .walletId(walletDeposit.getWalletId())
                .amount(walletDeposit.getAmount())
                .usableBalanceAmount(OppositePartyStatus.APPROVED.equals(oppositePartyStatus) ? walletDeposit.getAmount() : BigDecimal.ZERO)
                .build();

        Wallet wallet = walletJpaRepositoryAdapter.upsertBalance(balanceChange);

        WalletTxInfo walletTxInfo = WalletTxInfo.builder()
                .walletId(walletDeposit.getWalletId())
                .amount(walletDeposit.getAmount())
                .oppositePartyType(walletDeposit.getOppositePartyType())
                .oppositePartyStatus(oppositePartyStatus)
                .transactionType(walletDeposit.getTransactionType())
                .build();
        return walletTxJpaRepositoryAdapter.save(walletTxInfo);
    }

    @Transactional
    public WalletTx withdraw(WalletWithdraw walletWithdraw){
        OppositePartyStatus oppositePartyStatus = decideOppositePartyStatus(walletWithdraw.getAmount());
        BalanceChange balanceChange = BalanceChange.builder()
                .walletId(walletWithdraw.getWalletId())
                .amount(walletWithdraw.getAmount().negate())
                .usableBalanceAmount(OppositePartyStatus.APPROVED.equals(oppositePartyStatus) ? walletWithdraw.getAmount().negate() : BigDecimal.ZERO)
                .build();

        walletJpaRepositoryAdapter.upsertBalance(balanceChange);

        WalletTxInfo walletTxInfo = WalletTxInfo.builder()
                .walletId(walletWithdraw.getWalletId())
                .amount(walletWithdraw.getAmount())
                .oppositePartyType(walletWithdraw.getDestination())
                .oppositePartyStatus(oppositePartyStatus)
                .transactionType(walletWithdraw.getTransactionType())
                .build();
        return walletTxJpaRepositoryAdapter.save(walletTxInfo);
    }


    private OppositePartyStatus decideOppositePartyStatus(BigDecimal amount){
        if(TX_THRESHOLD.compareTo(amount) > 0) {
            return OppositePartyStatus.APPROVED;
        }
        else {
            return OppositePartyStatus.PENDING;
        }
    }
}
