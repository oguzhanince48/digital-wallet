package ing.Digital.Wallet.wallet_tx.controller;

import ing.Digital.Wallet.common.rest.BaseController;
import ing.Digital.Wallet.wallet.controller.response.model.DataResponse;
import ing.Digital.Wallet.wallet.controller.response.model.Response;
import ing.Digital.Wallet.wallet_tx.controller.request.WalletDepositRequest;
import ing.Digital.Wallet.wallet_tx.controller.request.WalletTxSearchRequest;
import ing.Digital.Wallet.wallet_tx.controller.request.WalletWithdrawRequest;
import ing.Digital.Wallet.wallet_tx.controller.response.WalletTxSearchResponse;
import ing.Digital.Wallet.wallet_tx.service.WalletTxService;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearchResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/wallet/v1/transactions")
@RequiredArgsConstructor
public class WalletTxController extends BaseController {

    private final WalletTxService walletTxService;

    @GetMapping
    public Response<DataResponse<WalletTxSearchResponse>> search(@Valid WalletTxSearchRequest walletTxSearchRequest) {
        WalletTxSearchResult walletTxSearchResult = walletTxService.search(walletTxSearchRequest.toModel());
        return respond(walletTxSearchResult.getWalletTxList().stream()
                .map(WalletTxSearchResponse::fromModel)
                .toList(), walletTxSearchResult.getPage(), walletTxSearchResult.getSize(), walletTxSearchResult.getTotalCount());
    }

    @PostMapping("/deposit")
    public Response<WalletTx> deposit(@Valid @RequestBody WalletDepositRequest walletDepositRequest) {
        WalletTx walletTx = walletTxService.deposit(walletDepositRequest.toModel());
        return respond(walletTx);
    }

    @PostMapping("/withdraw")
    public Response<WalletTx> deposit(@Valid @RequestBody WalletWithdrawRequest walletWithdrawRequest) {
        WalletTx walletTx = walletTxService.withdraw(walletWithdrawRequest.toModel());
        return respond(walletTx);
    }



}
