package ing.Digital.Wallet.wallet_tx.service;

import ing.Digital.Wallet.wallet_tx.service.model.WalletTransaction;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxApproval;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearch;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class WalletTxFacade {

    private final WalletTxService  walletTxService;
    private final WalletTxValidationService walletTxValidationService;
    private final WalletTxApprovalService walletTxApprovalService;

    public WalletTxSearchResult search(WalletTxSearch walletTxSearch){
        return walletTxService.search(walletTxSearch);
    }

    public WalletTx deposit(WalletTransaction walletTransaction){
        walletTxValidationService.validateDeposit(walletTransaction);
        return walletTxService.deposit(walletTransaction);
    }

    public WalletTx withdraw(WalletTransaction walletTransaction){
        walletTxValidationService.validateWithdraw(walletTransaction);
        return walletTxService.withdraw(walletTransaction);
    }

    public WalletTx processApproval(WalletTxApproval walletTxApproval){
        walletTxValidationService.validateApproval(walletTxApproval);
        return walletTxApprovalService.processApproval(walletTxApproval);
    }

}
