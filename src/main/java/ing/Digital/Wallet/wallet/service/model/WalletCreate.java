package ing.Digital.Wallet.wallet.service.model;

import ing.Digital.Wallet.currency.jpa.model.CurrencyCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletCreate {
    private String name;
    private Boolean isActiveForShopping;
    private Boolean isActiveForWithdraw;
    private CurrencyCode currencyCode;
    private Long customerId;
}
