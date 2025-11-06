package ing.Digital.Wallet.wallet_tx.service;

import ing.Digital.Wallet.wallet.jpa.WalletJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet.service.model.BalanceChange;
import ing.Digital.Wallet.wallet_tx.jpa.WalletTxJpaRepositoryAdapter;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyType;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxApproval;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletTxApprovalServiceTest {

    @InjectMocks
    WalletTxApprovalService walletTxApprovalService;
    @Mock
    WalletTxJpaRepositoryAdapter walletTxJpaRepositoryAdapter;
    @Mock
    WalletJpaRepositoryAdapter walletJpaRepositoryAdapter;


    @Test
    void approve_depositTransactionWhenApproved() {

        WalletTxApproval walletTxApproval = WalletTxApproval.builder()
                .transactionId(1L)
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .customerId(1L)
                .build();

        WalletTx existedTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyType(OppositePartyType.IBAN)
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .transactionType(TransactionType.DEPOSIT)
                .build();

        WalletTx updatedTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .transactionType(TransactionType.DEPOSIT)
                .build();

        when(walletTxJpaRepositoryAdapter.retrieve(any(Long.class))).thenReturn(existedTx);
        doNothing().when(walletJpaRepositoryAdapter).upsertBalance(any(BalanceChange.class));
        when(walletTxJpaRepositoryAdapter.updateStatus(any(WalletTx.class),any(WalletTxApproval.class))).thenReturn(updatedTx);

        // when
        WalletTx result = walletTxApprovalService.processApproval(walletTxApproval);

        // then
        assertNotNull(result);
        assertEquals(OppositePartyStatus.APPROVED, result.getOppositePartyStatus());
        assertEquals(existedTx.getId(), result.getId());
        assertEquals(existedTx.getAmount(), result.getAmount());
        assertEquals(TransactionType.DEPOSIT, result.getTransactionType());

        verify(walletTxJpaRepositoryAdapter).retrieve(walletTxApproval.getTransactionId());
        verify(walletJpaRepositoryAdapter).upsertBalance(any(BalanceChange.class));
        verify(walletTxJpaRepositoryAdapter).updateStatus(existedTx, walletTxApproval);
    }

    @Test
    void deny_depositTransactionWhenApproved() {

        WalletTxApproval walletTxApproval = WalletTxApproval.builder()
                .transactionId(1L)
                .oppositePartyStatus(OppositePartyStatus.DENIED)
                .customerId(1L)
                .build();

        WalletTx existedTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyType(OppositePartyType.IBAN)
                .oppositePartyStatus(OppositePartyStatus.DENIED)
                .transactionType(TransactionType.DEPOSIT)
                .build();

        WalletTx updatedTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyStatus(OppositePartyStatus.DENIED)
                .transactionType(TransactionType.DEPOSIT)
                .build();

        when(walletTxJpaRepositoryAdapter.retrieve(any(Long.class))).thenReturn(existedTx);
        doNothing().when(walletJpaRepositoryAdapter).upsertBalance(any(BalanceChange.class));
        when(walletTxJpaRepositoryAdapter.updateStatus(any(WalletTx.class),any(WalletTxApproval.class))).thenReturn(updatedTx);

        // when
        WalletTx result = walletTxApprovalService.processApproval(walletTxApproval);

        // then
        assertNotNull(result);
        assertEquals(OppositePartyStatus.DENIED, result.getOppositePartyStatus());
        assertEquals(existedTx.getId(), result.getId());
        assertEquals(existedTx.getAmount(), result.getAmount());
        assertEquals(TransactionType.DEPOSIT, result.getTransactionType());

        verify(walletTxJpaRepositoryAdapter).retrieve(walletTxApproval.getTransactionId());
        verify(walletJpaRepositoryAdapter).upsertBalance(any(BalanceChange.class));
        verify(walletTxJpaRepositoryAdapter).updateStatus(existedTx, walletTxApproval);
    }

    @Test
    void approve_withdrawTransactionWhenApproved() {

        WalletTxApproval walletTxApproval = WalletTxApproval.builder()
                .transactionId(1L)
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .customerId(1L)
                .build();

        WalletTx existedTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyType(OppositePartyType.IBAN)
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .transactionType(TransactionType.WITHDRAW)
                .build();

        WalletTx updatedTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyStatus(OppositePartyStatus.APPROVED)
                .transactionType(TransactionType.WITHDRAW)
                .build();

        when(walletTxJpaRepositoryAdapter.retrieve(any(Long.class))).thenReturn(existedTx);
        doNothing().when(walletJpaRepositoryAdapter).upsertBalance(any(BalanceChange.class));
        when(walletTxJpaRepositoryAdapter.updateStatus(any(WalletTx.class),any(WalletTxApproval.class))).thenReturn(updatedTx);

        // when
        WalletTx result = walletTxApprovalService.processApproval(walletTxApproval);

        // then
        assertNotNull(result);
        assertEquals(OppositePartyStatus.APPROVED, result.getOppositePartyStatus());
        assertEquals(existedTx.getId(), result.getId());
        assertEquals(existedTx.getAmount(), result.getAmount());
        assertEquals(TransactionType.WITHDRAW, result.getTransactionType());

        verify(walletTxJpaRepositoryAdapter).retrieve(walletTxApproval.getTransactionId());
        verify(walletJpaRepositoryAdapter).upsertBalance(any(BalanceChange.class));
        verify(walletTxJpaRepositoryAdapter).updateStatus(existedTx, walletTxApproval);
    }

    @Test
    void deny_withdrawTransactionWhenApproved() {

        WalletTxApproval walletTxApproval = WalletTxApproval.builder()
                .transactionId(1L)
                .oppositePartyStatus(OppositePartyStatus.DENIED)
                .customerId(1L)
                .build();

        WalletTx existedTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyType(OppositePartyType.IBAN)
                .oppositePartyStatus(OppositePartyStatus.DENIED)
                .transactionType(TransactionType.WITHDRAW)
                .build();

        WalletTx updatedTx = WalletTx.builder()
                .id(1L)
                .walletId(1L)
                .amount(BigDecimal.valueOf(1000))
                .oppositePartyStatus(OppositePartyStatus.DENIED)
                .transactionType(TransactionType.WITHDRAW)
                .build();

        when(walletTxJpaRepositoryAdapter.retrieve(any(Long.class))).thenReturn(existedTx);
        doNothing().when(walletJpaRepositoryAdapter).upsertBalance(any(BalanceChange.class));
        when(walletTxJpaRepositoryAdapter.updateStatus(any(WalletTx.class),any(WalletTxApproval.class))).thenReturn(updatedTx);

        // when
        WalletTx result = walletTxApprovalService.processApproval(walletTxApproval);

        // then
        assertNotNull(result);
        assertEquals(OppositePartyStatus.DENIED, result.getOppositePartyStatus());
        assertEquals(existedTx.getId(), result.getId());
        assertEquals(existedTx.getAmount(), result.getAmount());
        assertEquals(TransactionType.WITHDRAW, result.getTransactionType());

        verify(walletTxJpaRepositoryAdapter).retrieve(walletTxApproval.getTransactionId());
        verify(walletJpaRepositoryAdapter).upsertBalance(any(BalanceChange.class));
        verify(walletTxJpaRepositoryAdapter).updateStatus(existedTx, walletTxApproval);
    }
}