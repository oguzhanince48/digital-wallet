package ing.Digital.Wallet.wallet_tx.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletTxSearch {
    private Integer page;
    private Integer size;
    private Long walletId;
}
