package ing.Digital.Wallet.wallet_tx.controller.response;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class WalletTxSearchResponse {
    private Long id;
    private BigDecimal amount;
    private Long walletId;
    private TransactionType transactionType;
    private OppositePartyType oppositePartyType;
    private OppositePartyStatus oppositePartyStatus;

    public static WalletTxSearchResponse fromModel(WalletTx walletTx) {
        return WalletTxSearchResponse.builder()
                .id(walletTx.getId())
                .amount(walletTx.getAmount())
                .walletId(walletTx.getWalletId())
                .transactionType(walletTx.getTransactionType())
                .oppositePartyType(walletTx.getOppositePartyType())
                .oppositePartyStatus(walletTx.getOppositePartyStatus())
                .build();
    }
}
