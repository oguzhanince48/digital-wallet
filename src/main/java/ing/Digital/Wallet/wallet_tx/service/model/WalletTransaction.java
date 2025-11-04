package ing.Digital.Wallet.wallet_tx.service.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletTransaction {
    private BigDecimal amount;
    private Long walletId;
    private OppositePartyType oppositePartyType;
    private TransactionType transactionType;
    private Long customerId;
}
