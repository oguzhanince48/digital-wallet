package ing.Digital.Wallet.wallet_tx.jpa.entity;

import ing.Digital.Wallet.common.jpa.entity.AbstractEntity;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "wallet_tx")
public class WalletTxEntity extends AbstractEntity {
    private BigDecimal amount;
    private Long walletId;
    private OppositePartyType oppositePartyType;
    private OppositePartyStatus oppositePartyStatus;
    private TransactionType transactionType;


    public WalletTx toModel() {
        return WalletTx.builder()
                .id(getId())
                .createdDate(getCreatedDate())
                .updatedDate(getUpdatedDate())
                .amount(amount)
                .walletId(walletId)
                .oppositePartyType(oppositePartyType)
                .oppositePartyStatus(oppositePartyStatus)
                .transactionType(transactionType)
                .build();
    }
}
