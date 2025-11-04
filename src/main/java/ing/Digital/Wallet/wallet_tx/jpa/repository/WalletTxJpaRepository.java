package ing.Digital.Wallet.wallet_tx.jpa.repository;

import ing.Digital.Wallet.wallet_tx.jpa.entity.WalletTxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletTxJpaRepository extends JpaRepository<WalletTxEntity,Long> {
    List<WalletTxEntity> findAllById(Long id);
}
