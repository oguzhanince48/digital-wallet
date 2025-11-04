package ing.Digital.Wallet.customer.controller;
import ing.Digital.Wallet.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/wallet/v1/wallets")
public class CustomerController {
    private final WalletService walletService;

}
