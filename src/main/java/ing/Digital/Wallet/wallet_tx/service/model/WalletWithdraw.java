package ing.Digital.Wallet.wallet_tx.service.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletWithdraw {
    private BigDecimal amount;
    private Long walletId;
    private OppositePartyType destination;
    private OppositePartyStatus oppositePartyStatus;
    private TransactionType transactionType;
}
