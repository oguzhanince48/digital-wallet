package ing.Digital.Wallet.approval.service;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WalletTxApprovalValidator {
    public void validate(WalletTx walletTx, OppositePartyStatus oppositePartyStatus) {
        validateOppositePartyStatus(oppositePartyStatus);
        validateWalletTxStatus(walletTx);
    }

    private void validateOppositePartyStatus(OppositePartyStatus oppositePartyStatus) {
        if(!OppositePartyStatus.APPROVED.equals(oppositePartyStatus) && !OppositePartyStatus.DENIED.equals(oppositePartyStatus)) {
            throw new IllegalArgumentException("Invalid opposite party status: " + oppositePartyStatus); //TODO exception handling
        }
    }

    private void validateWalletTxStatus(WalletTx walletTx) {
        if(!OppositePartyStatus.PENDING.equals(walletTx.getOppositePartyStatus())) {
            throw new IllegalArgumentException("Wallet transaction is not in PENDING status: " + walletTx.getOppositePartyStatus()); //TODO exception handling
        }
    }
}
