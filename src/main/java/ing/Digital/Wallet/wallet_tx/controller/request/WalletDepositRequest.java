package ing.Digital.Wallet.wallet_tx.controller.request;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletDeposit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class WalletDepositRequest {
    private BigDecimal amount;
    private Long walletId;
    private OppositePartyType oppositePartyType;

    public WalletDeposit toModel() {
        return WalletDeposit.builder()
                .amount(amount)
                .walletId(walletId)
                .oppositePartyType(oppositePartyType)
                .transactionType(TransactionType.DEPOSIT)
                .build();
    }
}
