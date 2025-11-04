package ing.Digital.Wallet.wallet.service.model;

import ing.Digital.Wallet.currency.jpa.model.CurrencyCode;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletSearch {
    private Integer size;
    private Integer page;
    private Long customerId;
    private CurrencyCode currencyCode;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
}
