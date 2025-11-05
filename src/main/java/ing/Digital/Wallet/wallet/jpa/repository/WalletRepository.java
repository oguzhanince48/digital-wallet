package ing.Digital.Wallet.wallet.jpa.repository;

import ing.Digital.Wallet.common.exception.WalletApiBusinessException;
import ing.Digital.Wallet.customer.jpa.entity.CustomerEntity;
import ing.Digital.Wallet.wallet.jpa.entity.WalletEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class WalletRepository {
    private final WalletJpaRepository walletJpaRepository;

    public WalletEntity retrieveByIdAndCustomer(Long id, CustomerEntity customerEntity){
        return walletJpaRepository.findByIdAndCustomerEntity(id,customerEntity).orElseThrow(() -> new WalletApiBusinessException("wallet-api.wallet.notFound"));
    }

    public WalletEntity save(WalletEntity walletEntity){
        return walletJpaRepository.save(walletEntity);
    }

    public void updateBalanceAmount(Long id, BigDecimal amount, BigDecimal usableBalanceAmount){
        walletJpaRepository.updateBalanceAmount(id,amount,usableBalanceAmount);
    }
}
