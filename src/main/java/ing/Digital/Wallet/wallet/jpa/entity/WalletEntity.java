package ing.Digital.Wallet.wallet.jpa.entity;

import ing.Digital.Wallet.common.jpa.entity.AbstractStatusEntity;
import ing.Digital.Wallet.currency.jpa.entity.CurrencyEntity;
import ing.Digital.Wallet.currency.jpa.model.Currency;
import ing.Digital.Wallet.customer.jpa.entity.CustomerEntity;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "wallet")
public class WalletEntity extends AbstractStatusEntity {
    @Column(nullable = false)
    private String walletName;
    @Column(nullable = false)
    private Boolean isActiveForShopping;
    @Column(nullable = false)
    private Boolean isActiveForWithdraw;
    @Column(nullable = false)
    private BigDecimal balance;
    @Column(nullable = false)
    private BigDecimal usableBalance;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customerEntity;
    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private CurrencyEntity currencyEntity;

    public Wallet toModel() {
        return Wallet.builder()
                .walletName(walletName)
                .isActiveForShopping(isActiveForShopping)
                .isActiveForWithdraw(isActiveForWithdraw)
                .balance(balance)
                .usableBalance(usableBalance)
                .customer(customerEntity.toModel())
                .currency(currencyEntity.toModel())
                .build();
    }
}
