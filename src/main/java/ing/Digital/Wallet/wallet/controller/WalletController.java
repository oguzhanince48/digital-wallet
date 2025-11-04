package ing.Digital.Wallet.wallet.controller;
import ing.Digital.Wallet.common.rest.BaseController;
import ing.Digital.Wallet.wallet.controller.request.WalletCreateRequest;
import ing.Digital.Wallet.wallet.controller.response.WalletCreateResponse;
import ing.Digital.Wallet.wallet.controller.response.WalletSearchResponse;
import ing.Digital.Wallet.wallet.controller.request.WalletSearchRequest;
import ing.Digital.Wallet.wallet.controller.response.model.DataResponse;
import ing.Digital.Wallet.wallet.controller.response.model.Response;
import ing.Digital.Wallet.wallet.service.WalletService;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import ing.Digital.Wallet.wallet.service.model.WalletSearchResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/wallet/v1/wallets")
public class WalletController extends BaseController {

    private final WalletService walletService;

    @GetMapping
    public Response<DataResponse<WalletSearchResponse>> search(@Valid WalletSearchRequest walletSearchRequest, @RequestHeader("customerId") Long customerId) {
        WalletSearchResult walletSearchResult = walletService.search(walletSearchRequest.toModel(customerId));
        return respond(walletSearchResult.getWallets().stream()
                .map(WalletSearchResponse::fromModel)
                .toList(), walletSearchResult.getPage(), walletSearchResult.getSize(), walletSearchResult.getTotalCount());
    }

    @PostMapping("/create")
    public Response<WalletCreateResponse> create(@Valid WalletCreateRequest walletCreateRequest, @RequestHeader("customerId") Long customerId) {
        Wallet wallet = walletService.create(walletCreateRequest.toModel(customerId));
        return respond(WalletCreateResponse.fromModel(wallet));
    }
}
