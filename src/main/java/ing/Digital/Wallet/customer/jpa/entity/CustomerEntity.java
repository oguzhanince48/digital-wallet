package ing.Digital.Wallet.customer.jpa.entity;

import ing.Digital.Wallet.common.jpa.entity.AbstractStatusEntity;
import ing.Digital.Wallet.customer.service.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "customer")
@Entity
public class CustomerEntity extends AbstractStatusEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String identityNumber;

    public Customer toModel() {
        return Customer.builder()
                .id(super.getId())
                .createdDate(super.getCreatedDate())
                .updatedDate(super.getUpdatedDate())
                .status(super.getStatus())
                .name(name)
                .surname(surname)
                .identityNumber(identityNumber)
                .build();
    }
}
