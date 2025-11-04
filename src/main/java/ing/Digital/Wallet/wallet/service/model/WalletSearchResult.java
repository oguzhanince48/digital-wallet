package ing.Digital.Wallet.wallet.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WalletSearchResult {
    private Integer page;
    private Integer size;
    private Long totalCount;
    private List<Wallet> wallets;
}
