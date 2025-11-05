package ing.Digital.Wallet.wallet_tx.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletTxApproval {
    private Long transactionId;
    private OppositePartyStatus oppositePartyStatus;
    private Long customerId;
}
