package ing.Digital.Wallet.common.jpa.entity;

import ing.Digital.Wallet.common.model.Status;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractStatusEntity extends AbstractEntity {

    @Column(nullable = false)
    private Status status;
}
