package ing.Digital.Wallet.wallet_tx.jpa.repository;

import ing.Digital.Wallet.common.exception.WalletApiBusinessException;
import ing.Digital.Wallet.wallet_tx.jpa.entity.WalletTxEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WalletTxRepository {
    private final WalletTxJpaRepository walletTxJpaRepository;

    public WalletTxEntity save(WalletTxEntity walletTxEntity){
        return walletTxJpaRepository.save(walletTxEntity);
    }

    public WalletTxEntity retrieve(Long id){
        return walletTxJpaRepository.findById(id).orElseThrow(() -> new WalletApiBusinessException("wallet-api.walletTx.notFound"));
    }
}
