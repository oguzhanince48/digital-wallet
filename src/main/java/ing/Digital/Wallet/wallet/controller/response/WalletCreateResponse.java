package ing.Digital.Wallet.wallet.controller.response;

import ing.Digital.Wallet.common.model.Status;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class WalletCreateResponse extends WalletResponse {
    private LocalDateTime createdAt;

    public static WalletCreateResponse fromModel(Wallet wallet) {
        return WalletCreateResponse.builder()
                .id(wallet.getId())
                .walletName(wallet.getWalletName())
                .isActiveForShopping(wallet.getIsActiveForShopping())
                .isActiveForWithdraw(wallet.getIsActiveForWithdraw())
                .balance(wallet.getBalance())
                .usableBalance(wallet.getUsableBalance())
                .currency(wallet.getCurrency().getCode())
                .createdAt(wallet.getCreatedAt())
                .build();
    }
}
