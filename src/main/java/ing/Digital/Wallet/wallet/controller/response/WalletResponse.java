package ing.Digital.Wallet.wallet.controller.response;

import ing.Digital.Wallet.wallet.service.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class WalletResponse {
    private Long id;
    private LocalDateTime createdAt;
    private String walletName;
    private Boolean isActiveForShopping;
    private Boolean isActiveForWithdraw;
    private BigDecimal balance;
    private BigDecimal usableBalance;
    private String currency;
    private String customerName;
    private String customerSurname;
    private Long customerId;

    public static WalletResponse fromModel(Wallet wallet) {
        return WalletResponse.builder()
                .id(wallet.getId())
                .createdAt(wallet.getCreatedAt())
                .walletName(wallet.getWalletName())
                .isActiveForShopping(wallet.getIsActiveForShopping())
                .isActiveForWithdraw(wallet.getIsActiveForWithdraw())
                .balance(wallet.getBalance())
                .usableBalance(wallet.getUsableBalance())
                .customerName(wallet.getCustomer().getName())
                .customerSurname(wallet.getCustomer().getSurname())
                .currency(wallet.getCurrency().getCode())
                .customerId(wallet.getCustomer().getId())
                .build();
    }

}
