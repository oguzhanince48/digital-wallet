package ing.Digital.Wallet.wallet_tx.service;

import ing.Digital.Wallet.common.configuration.WalletConfiguration;
import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.BalanceChange;
import ing.Digital.Wallet.wallet.service.model.WalletSearch;
import ing.Digital.Wallet.wallet.service.model.WalletSearchResult;
import ing.Digital.Wallet.wallet_tx.jpa.WalletTxJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTransaction;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxInfo;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearch;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletTxServiceTest {

    @InjectMocks
    private WalletTxService walletTxService;

    @Mock
    private WalletTxJpaRepositoryAdapter walletTxRepository;

    @Mock
    private WalletJpaRepositoryAdapter walletJpaRepositoryAdapter;

    @Mock
    private WalletConfiguration walletConfiguration;

    @Test
    void search() {
        WalletTxSearch walletTxSearch = WalletTxSearch
                .builder()
                .size(0)
                .page(10)
                .walletId(1L)
                .build();

        List<WalletTx> walletTxs =List.of(WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .amount(BigDecimal.valueOf(100))
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .transactionType(TransactionType.DEPOSIT)
                .oppositePartyType(OppositePartyType.IBAN)
                .build());

        WalletTxSearchResult walletSearchResult = WalletTxSearchResult
                .builder()
                .walletTxList(walletTxs)
                .size(1)
                .page(0)
                .totalCount(1L)
                .build();

        when(walletTxRepository.search(walletTxSearch)).thenReturn(walletSearchResult);
        WalletTxSearchResult result = walletTxService.search(walletTxSearch);
        assertEquals(1L, result.getTotalCount());
        assertEquals(1L, result.getWalletTxList().size());
        assertEquals(walletTxSearch.getWalletId(), result.getWalletTxList().getFirst().getId());
    }

    @Test
    void deposit_smaller_than_limit() {
        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .walletId(1L)
                .amount(BigDecimal.valueOf(100))
                .oppositePartyType(OppositePartyType.IBAN)
                .transactionType(TransactionType.DEPOSIT)
                .customerId(1L)
                .build();

        BalanceChange balanceChange = BalanceChange.builder()
                .walletId(walletTransaction.getWalletId())
                .amount(walletTransaction.getAmount())
                .usableBalanceAmount(walletTransaction.getAmount())
                .customerId(walletTransaction.getCustomerId())
                .build();

        WalletTxInfo walletTxInfo = WalletTxInfo.builder()
                .walletId(walletTransaction.getWalletId())
                .amount(walletTransaction.getAmount())
                .oppositePartyType(walletTransaction.getOppositePartyType())
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .transactionType(walletTransaction.getTransactionType())
                .build();

        WalletTx walletTx = WalletTx.builder()
                .id(1L)
                .walletId(walletTransaction.getWalletId())
                .amount(walletTransaction.getAmount())
                .oppositePartyType(walletTransaction.getOppositePartyType())
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .transactionType(walletTransaction.getTransactionType())
                .build();

        when(walletConfiguration.getLimit()).thenReturn(BigDecimal.valueOf(1000));
        doNothing().when(walletJpaRepositoryAdapter).upsertBalance(balanceChange);
        when(walletTxRepository.save(walletTxInfo)).thenReturn(walletTx);

        WalletTx result = walletTxService.deposit(walletTransaction);
        assertNotNull(result);
        assertEquals(walletTx.getId(), result.getId());
        assertEquals(walletTransaction.getAmount(), result.getAmount());
        assertEquals(walletTransaction.getTransactionType(), result.getTransactionType());
        assertEquals(OppositePartyStatus.APPROVED, result.getOppositePartyStatus());
    }

    @Test
    void withdraw() {
    }
}