package ing.Digital.Wallet.currency.jpa.repository;

import ing.Digital.Wallet.common.exception.WalletApiBusinessException;
import ing.Digital.Wallet.currency.jpa.entity.CurrencyEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyRepository {
    private final CurrencyJpaRepository currencyJpaRepository;

    public CurrencyEntity findByCode(String code) {
        return currencyJpaRepository.findByCode(code).orElseThrow(() -> new WalletApiBusinessException("wallet-api.currency.notFound",code));
    }
}
