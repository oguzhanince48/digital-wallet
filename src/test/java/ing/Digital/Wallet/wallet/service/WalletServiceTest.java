package ing.Digital.Wallet.wallet.service;

import ing.Digital.Wallet.currency.jpa.model.Currency;
import ing.Digital.Wallet.currency.jpa.model.CurrencyCode;
import ing.Digital.Wallet.customer.service.model.Customer;
import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import ing.Digital.Wallet.wallet.service.model.WalletCreate;
import ing.Digital.Wallet.wallet.service.model.WalletSearch;
import ing.Digital.Wallet.wallet.service.model.WalletSearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletJpaRepositoryAdapter walletJpaRepositoryAdapter;


    @Test
    void searchWithCustomerId() {
        WalletSearch walletSearch = WalletSearch
                .builder()
                .size(0)
                .page(10)
                .currencyCode(CurrencyCode.TRY)
                .customerId(1L)
                .build();

        List<Wallet> wallets =List.of(Wallet.builder()
                .id(1L)
                .currency(Currency.builder().code(CurrencyCode.TRY.name()).build())
                .customer(Customer.builder().id(1L).build())
                .walletName("My Wallet")
                .balance(BigDecimal.ZERO)
                .usableBalance(BigDecimal.ZERO)
                .isActiveForShopping(true)
                .isActiveForWithdraw(true)
                .build());

        WalletSearchResult walletSearchResult = WalletSearchResult
                .builder()
                .wallets(wallets)
                .size(1)
                .page(0)
                .totalCount(1L)
                .build();

        when(walletJpaRepositoryAdapter.search(walletSearch)).thenReturn(walletSearchResult);

        WalletSearchResult result = walletService.search(walletSearch);

        assertNotNull(result);
        assertEquals(1, result.getWallets().size());
        assertThat(result.getWallets().getFirst().getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(result.getWallets().getFirst().getCurrency().getCode()).isEqualTo(CurrencyCode.TRY.name());
        assertThat(result.getWallets().getFirst().getCustomer().getId()).isEqualTo(1L);
        assertThat(result.getWallets().getFirst().getWalletName()).isEqualTo("My Wallet");
    }

    @Test
    void create() {
        WalletCreate walletCreate = WalletCreate
                .builder()
                .customerId(1L)
                .currencyCode(CurrencyCode.TRY)
                .name("My Wallet")
                .isActiveForShopping(true)
                .isActiveForWithdraw(true)
                .build();
        Wallet wallet = Wallet.builder()
                .id(1L)
                .currency(Currency.builder().code(CurrencyCode.TRY.name()).build())
                .customer(Customer.builder().id(1L).build())
                .walletName("My Wallet")
                .balance(BigDecimal.ZERO)
                .usableBalance(BigDecimal.ZERO)
                .isActiveForShopping(true)
                .isActiveForWithdraw(true)
                .build();

        when(walletJpaRepositoryAdapter.create(walletCreate)).thenReturn(wallet);

        Wallet result = walletService.create(walletCreate);
        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getWalletName()).isEqualTo("My Wallet");
        assertThat(result.getCurrency().getCode()).isEqualTo(CurrencyCode.TRY.name());
        assertThat(result.getCustomer().getId()).isEqualTo(1L);
        assertThat(result.getIsActiveForShopping()).isTrue();

    }
}