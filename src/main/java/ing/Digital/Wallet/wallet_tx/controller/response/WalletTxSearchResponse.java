package ing.Digital.Wallet.wallet_tx.controller.response;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class WalletTxSearchResponse {
    private Long id;
    private LocalDateTime transactionDate;
    private LocalDateTime transactionUpdateDate;
    private BigDecimal amount;
    private Long walletId;
    private TransactionType transactionType;
    private OppositePartyType oppositePartyType;
    private OppositePartyStatus oppositePartyStatus;

    public static WalletTxSearchResponse fromModel(WalletTx walletTx) {
        return WalletTxSearchResponse.builder()
                .id(walletTx.getId())
                .transactionDate(walletTx.getCreatedDate())
                .transactionUpdateDate(walletTx.getUpdatedDate())
                .amount(walletTx.getAmount())
                .walletId(walletTx.getWalletId())
                .transactionType(walletTx.getTransactionType())
                .oppositePartyType(walletTx.getOppositePartyType())
                .oppositePartyStatus(walletTx.getOppositePartyStatus())
                .build();
    }
}
