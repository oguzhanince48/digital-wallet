package ing.Digital.Wallet.wallet.controller.response;

import ing.Digital.Wallet.common.model.Status;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class WalletSearchResponse extends WalletResponse {
    private Status status;
    private String customerName;
    private String customerSurname;

    public static WalletSearchResponse fromModel(Wallet wallet) {
        return WalletSearchResponse.builder()
                .walletName(wallet.getWalletName())
                .isActiveForShopping(wallet.getIsActiveForShopping())
                .isActiveForWithdraw(wallet.getIsActiveForWithdraw())
                .balance(wallet.getBalance())
                .usableBalance(wallet.getUsableBalance())
                .customerName(wallet.getCustomer().getName())
                .customerSurname(wallet.getCustomer().getSurname())
                .currency(wallet.getCurrency().getCode())
                .build();
    }
}
