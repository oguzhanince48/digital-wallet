package ing.Digital.Wallet.wallet_tx.service.model;

import ing.Digital.Wallet.wallet.service.model.Wallet;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WalletTxSearchResult {
    private Integer page;
    private Integer size;
    private Long totalCount;
    private List<WalletTx> walletTxList;
}
