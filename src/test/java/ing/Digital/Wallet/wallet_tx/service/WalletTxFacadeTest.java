package ing.Digital.Wallet.wallet_tx.service;

import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTransaction;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class WalletTxFacadeTest {

    @InjectMocks
    WalletTxFacade walletTxFacade;

    @Mock
    WalletTxService walletTxService;

    @Mock
    WalletTxValidationService walletTxValidationService;

    @Test
    void deposit_shouldBeApprovedWhenAmountSmallerThanLimit() {
        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .walletId(1L)
                .amount(BigDecimal.valueOf(100))
                .oppositePartyType(OppositePartyType.IBAN)
                .transactionType(TransactionType.DEPOSIT)
                .customerId(1L)
                .build();

        WalletTx walletTx = WalletTx.builder()
                .id(1L)
                .walletId(walletTransaction.getWalletId())
                .amount(walletTransaction.getAmount())
                .oppositePartyType(walletTransaction.getOppositePartyType())
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .transactionType(walletTransaction.getTransactionType())
                .build();

        doNothing().when(walletTxValidationService).validateDeposit(walletTransaction);
        when(walletTxService.deposit(walletTransaction)).thenReturn(walletTx);

        WalletTx result = walletTxFacade.deposit(walletTransaction);

        assertNotNull(result);
        assertEquals(OppositePartyStatus.APPROVED, result.getOppositePartyStatus());
        assertEquals(walletTransaction.getAmount(), result.getAmount());
        assertEquals(walletTransaction.getOppositePartyType(), result.getOppositePartyType());

        verify(walletTxValidationService).validateDeposit(walletTransaction);
        verify(walletTxService).deposit(walletTransaction);
    }

    @Test
    void deposit_shouldBePendingWhenAmountSmallerThanLimit() {
        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyType(OppositePartyType.IBAN)
                .transactionType(TransactionType.DEPOSIT)
                .customerId(1L)
                .build();

        WalletTx walletTx = WalletTx.builder()
                .id(1L)
                .walletId(walletTransaction.getWalletId())
                .amount(walletTransaction.getAmount())
                .oppositePartyType(walletTransaction.getOppositePartyType())
                .oppositePartyStatus(OppositePartyStatus.PENDING)
                .transactionType(walletTransaction.getTransactionType())
                .build();

        doNothing().when(walletTxValidationService).validateDeposit(walletTransaction);
        when(walletTxService.deposit(walletTransaction)).thenReturn(walletTx);

        WalletTx result = walletTxFacade.deposit(walletTransaction);

        assertNotNull(result);
        assertEquals(OppositePartyStatus.PENDING, result.getOppositePartyStatus());
        assertEquals(walletTransaction.getAmount(), result.getAmount());
        assertEquals(walletTransaction.getOppositePartyType(), result.getOppositePartyType());

        verify(walletTxValidationService).validateDeposit(walletTransaction);
        verify(walletTxService).deposit(walletTransaction);
    }

    @Test
    void withdraw_shouldBeApprovedWhenAmountSmallerThanLimit() {
        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .walletId(1L)
                .amount(BigDecimal.valueOf(100))
                .oppositePartyType(OppositePartyType.IBAN)
                .transactionType(TransactionType.WITHDRAW)
                .customerId(1L)
                .build();

        WalletTx walletTx = WalletTx.builder()
                .id(1L)
                .walletId(walletTransaction.getWalletId())
                .amount(walletTransaction.getAmount())
                .oppositePartyType(walletTransaction.getOppositePartyType())
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .transactionType(walletTransaction.getTransactionType())
                .build();

        doNothing().when(walletTxValidationService).validateDeposit(walletTransaction);
        when(walletTxService.deposit(walletTransaction)).thenReturn(walletTx);

        WalletTx result = walletTxFacade.deposit(walletTransaction);

        assertNotNull(result);
        assertEquals(OppositePartyStatus.APPROVED, result.getOppositePartyStatus());
        assertEquals(walletTransaction.getAmount(), result.getAmount());
        assertEquals(walletTransaction.getOppositePartyType(), result.getOppositePartyType());
        assertEquals(walletTransaction.getTransactionType(), result.getTransactionType());

        verify(walletTxValidationService).validateDeposit(walletTransaction);
        verify(walletTxService).deposit(walletTransaction);
    }

    @Test
    void withdraw_shouldBePendingWhenAmountSmallerThanLimit() {
        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyType(OppositePartyType.IBAN)
                .transactionType(TransactionType.WITHDRAW)
                .customerId(1L)
                .build();

        WalletTx walletTx = WalletTx.builder()
                .id(1L)
                .walletId(walletTransaction.getWalletId())
                .amount(walletTransaction.getAmount())
                .oppositePartyType(walletTransaction.getOppositePartyType())
                .oppositePartyStatus(OppositePartyStatus.PENDING)
                .transactionType(walletTransaction.getTransactionType())
                .build();

        doNothing().when(walletTxValidationService).validateDeposit(walletTransaction);
        when(walletTxService.deposit(walletTransaction)).thenReturn(walletTx);

        WalletTx result = walletTxFacade.deposit(walletTransaction);

        assertNotNull(result);
        assertEquals(OppositePartyStatus.PENDING, result.getOppositePartyStatus());
        assertEquals(walletTransaction.getAmount(), result.getAmount());
        assertEquals(walletTransaction.getOppositePartyType(), result.getOppositePartyType());
        assertEquals(walletTransaction.getTransactionType(), result.getTransactionType());

        verify(walletTxValidationService).validateDeposit(walletTransaction);
        verify(walletTxService).deposit(walletTransaction);
    }
    @Test
    void processApproval() {
    }
}