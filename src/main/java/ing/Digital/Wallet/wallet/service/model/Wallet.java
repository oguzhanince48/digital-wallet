package ing.Digital.Wallet.wallet.service.model;

import ing.Digital.Wallet.common.model.Status;
import ing.Digital.Wallet.currency.jpa.model.Currency;
import ing.Digital.Wallet.customer.service.model.Customer;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Wallet {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private String walletName;
    private Boolean isActiveForShopping;
    private Boolean isActiveForWithdraw;
    private BigDecimal balance;
    private BigDecimal usableBalance;
    private Customer customer;
    private Currency currency;
}
