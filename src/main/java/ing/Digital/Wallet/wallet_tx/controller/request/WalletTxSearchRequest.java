package ing.Digital.Wallet.wallet_tx.controller.request;

import ing.Digital.Wallet.currency.jpa.model.CurrencyCode;
import ing.Digital.Wallet.wallet.service.model.WalletSearch;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearch;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

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

    public WalletTxSearch toModel() {
        return WalletTxSearch.builder()
                .page(page)
                .size(0)
                .walletId(walletId)
                .build();
    }
}
