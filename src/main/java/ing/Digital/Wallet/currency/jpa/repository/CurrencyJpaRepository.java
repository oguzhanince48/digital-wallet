package ing.Digital.Wallet.currency.jpa.repository;

import ing.Digital.Wallet.currency.jpa.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyJpaRepository extends JpaRepository<CurrencyEntity,Long> {
    Optional<CurrencyEntity> findByCode(String code);
}
