package ing.Digital.Wallet.wallet.controller.request;

import ing.Digital.Wallet.currency.jpa.model.Currency;
import ing.Digital.Wallet.currency.jpa.model.CurrencyCode;
import ing.Digital.Wallet.wallet.service.model.WalletSearch;
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
public class WalletSearchRequest {
    @Min(0)
    @Builder.Default
    private Integer page = 0;
    @Min(1)
    @Builder.Default
    private Integer size = 10;

    @Builder.Default
    private CurrencyCode currency = CurrencyCode.TRY;
    @Builder.Default
    private BigDecimal minAmount = BigDecimal.ZERO;
    private BigDecimal maxAmount;

    public WalletSearch toModel(Long customerId) {
        return WalletSearch.builder()
                .customerId(customerId)
                .currencyCode(currency)
                .minAmount(minAmount)
                .maxAmount(maxAmount)
                .build();
    }
}
