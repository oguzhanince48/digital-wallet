package ing.Digital.Wallet.currency.jpa.entity;

import ing.Digital.Wallet.common.jpa.entity.AbstractEntity;
import ing.Digital.Wallet.currency.jpa.model.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "currency")
@Getter
@Setter
public class CurrencyEntity extends AbstractEntity {
    @Column(nullable = false)
    private String code;

    public Currency toModel() {
        return Currency.builder()
                .id(super.getId())
                .code(code)
                .build();
    }
}
