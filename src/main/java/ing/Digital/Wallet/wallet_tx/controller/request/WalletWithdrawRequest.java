package ing.Digital.Wallet.wallet_tx.controller.request;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletDeposit;
import ing.Digital.Wallet.wallet_tx.service.model.WalletWithdraw;
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
    private OppositePartyType destination;

    public WalletWithdraw toModel() {
        return WalletWithdraw.builder()
                .amount(amount)
                .walletId(walletId)
                .destination(destination)
                .transactionType(TransactionType.WITHDRAW)
                .build();
    }
}
