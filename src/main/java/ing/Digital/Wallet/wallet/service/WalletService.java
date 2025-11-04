package ing.Digital.Wallet.wallet.service;

import ing.Digital.Wallet.wallet.controller.request.WalletCreateRequest;
import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import ing.Digital.Wallet.wallet.service.model.WalletCreate;
import ing.Digital.Wallet.wallet.service.model.WalletSearch;
import ing.Digital.Wallet.wallet.service.model.WalletSearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class WalletService {
    private final WalletJpaRepositoryAdapter walletJpaRepositoryAdapter;

    public WalletSearchResult search(WalletSearch walletSearch) {
        return walletJpaRepositoryAdapter.search(walletSearch);
    }

    public Wallet create(WalletCreate walletCreate){
        return walletJpaRepositoryAdapter.create(walletCreate);
    }
}
