package ing.Digital.Wallet.wallet_tx.service;

import ing.Digital.Wallet.common.exception.WalletApiBusinessException;
import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import ing.Digital.Wallet.wallet_tx.jpa.WalletTxJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTransaction;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxApproval;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Service
public class WalletTxValidationService {
    private final WalletJpaRepositoryAdapter walletJpaRepositoryAdapter;
    private final WalletTxJpaRepositoryAdapter walletTxJpaRepositoryAdapter;

    public void validateDeposit(WalletTransaction walletTransaction) {
        Wallet wallet = retrieveWallet(walletTransaction.getWalletId(),walletTransaction.getWalletId());
        validateDepositEnabled(wallet);
    }

    public void validateWithdraw(WalletTransaction walletTransaction) {
        Wallet wallet = retrieveWallet(walletTransaction.getWalletId(),walletTransaction.getWalletId());
        validateWithdrawEnabled(wallet);
        validateSufficientBalanceByTransactionType(wallet, walletTransaction.getAmount(),walletTransaction.getTransactionType());
    }

    public void validateApproval(WalletTxApproval walletTxApproval) {
        WalletTx walletTx = retrieveWalletTx(walletTxApproval.getTransactionId());
        Wallet wallet = retrieveWallet(walletTx.getWalletId(),walletTxApproval.getCustomerId());
        validateWalletTxOppositePartyStatus(walletTx);
        validateOppositePartyStatus(walletTxApproval.getOppositePartyStatus());
        validateWalletBelongsToCustomer(wallet, walletTxApproval.getCustomerId());
    }

    private void validateSufficientBalanceByTransactionType(Wallet wallet, BigDecimal amount, TransactionType transactionType) {
        if (TransactionType.WITHDRAW.equals(transactionType)) {
            validateSufficientBalance(wallet, amount);
        }
    }

    private void validateSufficientBalance(Wallet wallet, BigDecimal amount) {
        if (wallet.getUsableBalance().compareTo(amount) < 0) {
            throw new WalletApiBusinessException("wallet-api.insufficient.funds", wallet.getUsableBalance().toString());
        }
    }

    private void validateWalletBelongsToCustomer(Wallet wallet, Long customerId) {
        if (!customerId.equals(wallet.getCustomer().getId())) {
            throw new WalletApiBusinessException("wallet-api.wallet.notBelongToCustomer");
        }
    }

    private void validateOppositePartyStatus(OppositePartyStatus oppositePartyStatus) {
        if(!OppositePartyStatus.APPROVED.equals(oppositePartyStatus) && !OppositePartyStatus.DENIED.equals(oppositePartyStatus)) {
            throw new WalletApiBusinessException("wallet-api.invalid.oppositePartyStatus",oppositePartyStatus.name());
        }
    }

    private void validateWalletTxOppositePartyStatus(WalletTx walletTx) {
        if(!OppositePartyStatus.PENDING.equals(walletTx.getOppositePartyStatus())) {
            throw new WalletApiBusinessException("wallet-api.oppositePartyStatus.notPending");
        }
    }

    private void validateWithdrawEnabled(Wallet wallet) {
        if (!wallet.getIsActiveForWithdraw()) {
            throw new WalletApiBusinessException("wallet-api.withdraw.disabled");
        }
    }

    private void validateDepositEnabled(Wallet wallet) {
        if (!wallet.getIsActiveForShopping()) {
            throw new WalletApiBusinessException("wallet-api.deposit.disabled");
        }
    }

    private Wallet retrieveWallet(Long walletId, Long customerId) {
        return walletJpaRepositoryAdapter.retrieve(walletId, customerId);
    }

    private WalletTx retrieveWalletTx(Long transactionId) {
        return walletTxJpaRepositoryAdapter.retrieve(transactionId);
    }



}
