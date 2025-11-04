package ing.Digital.Wallet.wallet.controller.response;

import ing.Digital.Wallet.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private Status status;
    private String walletName;
    private Boolean isActiveForShopping;
    private Boolean isActiveForWithdraw;
    private BigDecimal balance;
    private BigDecimal usableBalance;
    private String currency;
}
