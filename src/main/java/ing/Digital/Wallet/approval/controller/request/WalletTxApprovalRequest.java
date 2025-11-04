package ing.Digital.Wallet.approval.controller.request;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import lombok.Data;

@Data
public class WalletTxApprovalRequest {
    private Long transactionId;
    private OppositePartyStatus oppositePartyStatus;
}
