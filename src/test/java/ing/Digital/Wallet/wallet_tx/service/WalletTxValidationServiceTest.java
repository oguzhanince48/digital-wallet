package ing.Digital.Wallet.wallet_tx.service;

import ing.Digital.Wallet.common.exception.WalletApiBusinessException;
import ing.Digital.Wallet.customer.service.model.Customer;
import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import ing.Digital.Wallet.wallet_tx.jpa.WalletTxJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTransaction;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxApproval;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletTxValidationServiceTest {

    @InjectMocks
    WalletTxValidationService walletTxValidationService;

    @Mock
    WalletJpaRepositoryAdapter walletJpaRepositoryAdapter;
    @Mock
    WalletTxJpaRepositoryAdapter walletTxJpaRepositoryAdapter;

    @Test
    void deposit_shouldBeValidateWhenEnabled() {
        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .walletId(1L)
                .amount(BigDecimal.valueOf(100))
                .oppositePartyType(OppositePartyType.IBAN)
                .transactionType(TransactionType.DEPOSIT)
                .customerId(1L)
                .build();

        Wallet wallet = Wallet.builder()
                .id(1L)
                .isActiveForShopping(true)
                .build();

        when(walletJpaRepositoryAdapter.retrieve(walletTransaction.getWalletId(),walletTransaction.getCustomerId())).thenReturn(wallet);

        assertDoesNotThrow(() -> walletTxValidationService.validateDeposit(walletTransaction));

        verify(walletJpaRepositoryAdapter).retrieve(1L, 1L);
    }

    @Test
    void deposit_shouldThrowExceptionWhenDisabled() {
        // given
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .walletId(2L)
                .amount(BigDecimal.valueOf(500))
                .transactionType(TransactionType.DEPOSIT)
                .customerId(2L)
                .build();

        Wallet wallet = Wallet.builder()
                .id(2L)
                .isActiveForShopping(false)
                .build();

        when(walletJpaRepositoryAdapter.retrieve(2L, 2L)).thenReturn(wallet);

        WalletApiBusinessException exception = assertThrows(
                WalletApiBusinessException.class,
                () -> walletTxValidationService.validateDeposit(walletTransaction)
        );

        assertEquals("wallet-api.deposit.disabled", exception.getMessage());

        verify(walletJpaRepositoryAdapter).retrieve(2L, 2L);
    }

    @Test
    void withdraw_shouldBeValidateWhenEnabled() {
        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .walletId(1L)
                .amount(BigDecimal.valueOf(100))
                .oppositePartyType(OppositePartyType.IBAN)
                .transactionType(TransactionType.WITHDRAW)
                .customerId(1L)
                .build();

        Wallet wallet = Wallet.builder()
                .id(1L)
                .isActiveForWithdraw(true)
                .usableBalance(BigDecimal.valueOf(100))
                .build();

        when(walletJpaRepositoryAdapter.retrieve(walletTransaction.getWalletId(),walletTransaction.getCustomerId())).thenReturn(wallet);

        assertDoesNotThrow(() -> walletTxValidationService.validateWithdraw(walletTransaction));

        verify(walletJpaRepositoryAdapter).retrieve(1L, 1L);
    }

    @Test
    void withdraw_shouldThrowExceptionWhenDisabled() {
        // given
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .walletId(2L)
                .amount(BigDecimal.valueOf(500))
                .transactionType(TransactionType.WITHDRAW)
                .customerId(2L)
                .build();

        Wallet wallet = Wallet.builder()
                .id(2L)
                .isActiveForWithdraw(false)
                .usableBalance(BigDecimal.valueOf(100))
                .build();

        when(walletJpaRepositoryAdapter.retrieve(2L, 2L)).thenReturn(wallet);

        WalletApiBusinessException exception = assertThrows(
                WalletApiBusinessException.class,
                () -> walletTxValidationService.validateWithdraw(walletTransaction)
        );

        assertEquals("wallet-api.withdraw.disabled", exception.getMessage());

        verify(walletJpaRepositoryAdapter).retrieve(2L, 2L);
    }

    @Test
    void withdraw_shouldThrowExceptionWhenNotHaveEnoughBalance() {
        // given
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .walletId(2L)
                .amount(BigDecimal.valueOf(500))
                .transactionType(TransactionType.WITHDRAW)
                .customerId(2L)
                .build();

        Wallet wallet = Wallet.builder()
                .id(2L)
                .isActiveForWithdraw(true)
                .usableBalance(BigDecimal.valueOf(100))
                .build();

        when(walletJpaRepositoryAdapter.retrieve(2L, 2L)).thenReturn(wallet);

        WalletApiBusinessException exception = assertThrows(
                WalletApiBusinessException.class,
                () -> walletTxValidationService.validateWithdraw(walletTransaction)
        );

        assertEquals("wallet-api.insufficient.funds", exception.getMessage());

        verify(walletJpaRepositoryAdapter).retrieve(2L, 2L);
    }

    @Test
    void approve_shouldCompleteSuccessfully() {
        WalletTxApproval walletTxApproval = WalletTxApproval.builder()
                .transactionId(1L)
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .customerId(1L)
                .build();

        WalletTx walletTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .oppositePartyStatus(OppositePartyStatus.PENDING)
                .build();

        Wallet wallet = Wallet.builder()
                .id(1L)
                .customer(Customer.builder().id(1L).build())
                .id(1L)
                .build();
        when(walletTxJpaRepositoryAdapter.retrieve(walletTxApproval.getTransactionId())).thenReturn(walletTx);
        when(walletJpaRepositoryAdapter.retrieve(walletTx.getWalletId(),walletTxApproval.getCustomerId())).thenReturn(wallet);
        assertDoesNotThrow(() -> walletTxValidationService.validateApproval(walletTxApproval));
        verify(walletTxJpaRepositoryAdapter).retrieve(1L);
        verify(walletJpaRepositoryAdapter).retrieve(1L,1L);
    }

    @Test
    void approve_shouldThrowExWhenTransactionStatusNotPending() {
        WalletTxApproval walletTxApproval = WalletTxApproval.builder()
                .transactionId(1L)
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .customerId(1L)
                .build();

        WalletTx walletTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .oppositePartyStatus(OppositePartyStatus.DENIED)
                .build();

        Wallet wallet = Wallet.builder()
                .id(1L)
                .customer(Customer.builder().id(1L).build())
                .id(1L)
                .build();
        when(walletTxJpaRepositoryAdapter.retrieve(walletTxApproval.getTransactionId())).thenReturn(walletTx);
        when(walletJpaRepositoryAdapter.retrieve(walletTx.getWalletId(),walletTxApproval.getCustomerId())).thenReturn(wallet);
        WalletApiBusinessException exception = assertThrows(
                WalletApiBusinessException.class,
                () -> walletTxValidationService.validateApproval(walletTxApproval)
        );

        assertEquals("wallet-api.oppositePartyStatus.notPending", exception.getMessage());

        verify(walletTxJpaRepositoryAdapter).retrieve(1L);
        verify(walletJpaRepositoryAdapter).retrieve(1L,1L);
    }

    @Test
    void approve_shouldThrowExWhenOppositePartyStatusInvalid() {
        WalletTxApproval walletTxApproval = WalletTxApproval.builder()
                .transactionId(1L)
                .oppositePartyStatus(OppositePartyStatus.PENDING)
                .customerId(1L)
                .build();

        WalletTx walletTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .oppositePartyStatus(OppositePartyStatus.PENDING)
                .build();

        Wallet wallet = Wallet.builder()
                .id(1L)
                .customer(Customer.builder().id(1L).build())
                .id(1L)
                .build();
        when(walletTxJpaRepositoryAdapter.retrieve(walletTxApproval.getTransactionId())).thenReturn(walletTx);
        when(walletJpaRepositoryAdapter.retrieve(walletTx.getWalletId(),walletTxApproval.getCustomerId())).thenReturn(wallet);
        WalletApiBusinessException exception = assertThrows(
                WalletApiBusinessException.class,
                () -> walletTxValidationService.validateApproval(walletTxApproval)
        );

        assertEquals("wallet-api.invalid.oppositePartyStatus", exception.getMessage());

        verify(walletTxJpaRepositoryAdapter).retrieve(1L);
        verify(walletJpaRepositoryAdapter).retrieve(1L,1L);
    }
}