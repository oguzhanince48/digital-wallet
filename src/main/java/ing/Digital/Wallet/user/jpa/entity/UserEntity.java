package ing.Digital.Wallet.user.jpa.entity;

import ing.Digital.Wallet.common.jpa.entity.AbstractEntity;
import ing.Digital.Wallet.user.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "users")
@Getter

public class UserEntity extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private Long customerId; // will be null for admins

}
