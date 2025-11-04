package ing.Digital.Wallet.wallet_tx.service;

import ing.Digital.Wallet.common.configuration.WalletConfiguration;
import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.BalanceChange;
import ing.Digital.Wallet.wallet_tx.jpa.WalletTxJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTransaction;
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
    private final WalletConfiguration walletConfiguration;

    @Transactional(readOnly = true)
    public WalletTxSearchResult search(WalletTxSearch walletTxSearch) {
        return walletTxJpaRepositoryAdapter.search(walletTxSearch);
    }

    @Transactional
    public WalletTx deposit(WalletTransaction walletTransaction){
        OppositePartyStatus oppositePartyStatus = decideOppositePartyStatus(walletTransaction.getAmount());
        BalanceChange balanceChange = buildBalanceChange(walletTransaction,oppositePartyStatus);
        walletJpaRepositoryAdapter.upsertBalance(balanceChange);
        WalletTxInfo walletTxInfo = buildWalletTxInfo(walletTransaction,oppositePartyStatus);
        return walletTxJpaRepositoryAdapter.save(walletTxInfo);
    }

    @Transactional
    public WalletTx withdraw(WalletTransaction walletTransaction){
        // TODO add balance check before withdraw
        OppositePartyStatus oppositePartyStatus = decideOppositePartyStatus(walletTransaction.getAmount());
        BalanceChange balanceChange = buildBalanceChange(walletTransaction,oppositePartyStatus);
        walletJpaRepositoryAdapter.upsertBalance(balanceChange);
        WalletTxInfo walletTxInfo = buildWalletTxInfo(walletTransaction,oppositePartyStatus);

        //add current balance with reusable balance to walletTxInfo
        return walletTxJpaRepositoryAdapter.save(walletTxInfo);
    }

    private OppositePartyStatus decideOppositePartyStatus(BigDecimal amount){
        if(walletConfiguration.getLimit().compareTo(amount) > 0) {
            return OppositePartyStatus.APPROVED;
        }
        else {
            return OppositePartyStatus.PENDING;
        }
    }

    private BalanceChange buildBalanceChange(WalletTransaction walletTransaction,OppositePartyStatus oppositePartyStatus){
        switch (walletTransaction.getTransactionType()){
            case DEPOSIT -> {
                return BalanceChange.builder()
                        .walletId(walletTransaction.getWalletId())
                        .amount(walletTransaction.getAmount())
                        .usableBalanceAmount(OppositePartyStatus.APPROVED.equals(oppositePartyStatus) ? walletTransaction.getAmount() : BigDecimal.ZERO)
                        .customerId(walletTransaction.getCustomerId())
                        .build();
            }
            case WITHDRAW -> {
                return BalanceChange.builder()
                        .walletId(walletTransaction.getWalletId())
                        .amount(walletTransaction.getAmount().negate())
                        .usableBalanceAmount(OppositePartyStatus.APPROVED.equals(oppositePartyStatus) ? walletTransaction.getAmount().negate() : BigDecimal.ZERO)
                        .customerId(walletTransaction.getCustomerId())
                        .build();
        }
            default -> throw new IllegalArgumentException("Unsupported transaction type: " + walletTransaction.getTransactionType());
        }
    }

    private WalletTxInfo buildWalletTxInfo(WalletTransaction walletTransaction,OppositePartyStatus oppositePartyStatus){
        return WalletTxInfo.builder()
                .walletId(walletTransaction.getWalletId())
                .amount(walletTransaction.getAmount())
                .oppositePartyType(walletTransaction.getOppositePartyType())
                .oppositePartyStatus(oppositePartyStatus)
                .transactionType(walletTransaction.getTransactionType())
                .build();
    }
}
