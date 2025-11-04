package ing.Digital.Wallet.wallet.service.model;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceChange {
    private BigDecimal amount;
    private BigDecimal usableBalanceAmount;
    private Long walletId;
}
