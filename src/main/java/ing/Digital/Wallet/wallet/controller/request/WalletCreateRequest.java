package ing.Digital.Wallet.wallet.controller.request;

import ing.Digital.Wallet.currency.jpa.model.CurrencyCode;
import ing.Digital.Wallet.wallet.service.model.WalletCreate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class WalletCreateRequest {
    @NotBlank
    private String name;
    @Builder.Default
    private Boolean isActiveForShopping = Boolean.TRUE;
    @Builder.Default
    private Boolean isActiveForWithdraw = Boolean.TRUE;
    private CurrencyCode currencyCode;

    public WalletCreate toModel(Long customerId) {
        return WalletCreate
            .builder()
            .customerId(customerId)
            .currencyCode(currencyCode)
            .isActiveForShopping(isActiveForShopping)
            .isActiveForWithdraw(isActiveForWithdraw)
            .name(name)
            .build();}
}
