package ing.Digital.Wallet.wallet.jpa.repository;

import ing.Digital.Wallet.wallet.jpa.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface WalletJpaRepository extends JpaRepository<WalletEntity,Long> {
    @Modifying
    @Query(value = "update wallet set balance = balance + :amount, usable_balance = usable_balance + :usableBalanceAmount, udate = now() where id = :id", nativeQuery = true)
    WalletEntity updateBalanceAmount(@Param("id") Long id,
                             @Param("amount") BigDecimal amount, @Param("usableBalanceAmount") BigDecimal usableBalanceAmount);

}
