package ing.Digital.Wallet.wallet_tx.service.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class WalletTx {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private BigDecimal amount;
    private Long walletId;
    private TransactionType transactionType;
    private OppositePartyType oppositePartyType;
    private OppositePartyStatus oppositePartyStatus;
}
