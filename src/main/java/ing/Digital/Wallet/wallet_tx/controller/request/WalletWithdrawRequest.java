package ing.Digital.Wallet.wallet_tx.controller.request;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class WalletWithdrawRequest {
    private BigDecimal amount;
    private Long walletId;
    private OppositePartyType oppositePartyType;

    public WalletTransaction toModel(Long customerId) {
        return WalletTransaction.builder()
                .amount(amount)
                .walletId(walletId)
                .oppositePartyType(oppositePartyType)
                .transactionType(TransactionType.WITHDRAW)
                .customerId(customerId)
                .build();
    }
}
