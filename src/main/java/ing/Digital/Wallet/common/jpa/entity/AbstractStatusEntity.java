package ing.Digital.Wallet.common.jpa.entity;

import ing.Digital.Wallet.common.model.Status;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@MappedSuperclass
@SQLRestriction("status != -1")
public abstract class AbstractStatusEntity extends AbstractEntity {

    @Column(nullable = false)
    private Status status;
}
