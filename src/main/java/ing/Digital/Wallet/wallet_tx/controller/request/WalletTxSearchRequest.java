package ing.Digital.Wallet.wallet_tx.controller.request;

import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearch;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class WalletTxSearchRequest {
    @Min(0)
    @Builder.Default
    private Integer page = 0;
    @Min(1)
    @Builder.Default
    private Integer size = 10;

    private Long walletId;

    public WalletTxSearch toModel(Long customerId) {
        return WalletTxSearch.builder()
                .page(page)
                .size(0)
                .walletId(walletId)
                .customerId(customerId)
                .build();
    }
}
