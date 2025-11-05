package ing.Digital.Wallet.wallet_tx.controller.request;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxApproval;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class WalletTxApprovalRequest {
    private Long transactionId;
    private OppositePartyStatus status;

    public WalletTxApproval toModel(Long customerId) {
        return WalletTxApproval.builder()
                .transactionId(transactionId)
                .oppositePartyStatus(status)
                .customerId(customerId)
                .build();
    }
}
