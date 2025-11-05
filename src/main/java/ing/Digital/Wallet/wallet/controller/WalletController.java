package ing.Digital.Wallet.wallet.controller;

import ing.Digital.Wallet.common.rest.BaseController;
import ing.Digital.Wallet.wallet.controller.request.WalletCreateRequest;
import ing.Digital.Wallet.wallet.controller.request.WalletSearchRequest;
import ing.Digital.Wallet.wallet.controller.response.WalletResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/wallet/v1/wallets")
public class WalletController extends BaseController {

    private final WalletService walletService;

    @GetMapping
    public Response<DataResponse<WalletResponse>> search(@Valid WalletSearchRequest walletSearchRequest, @RequestHeader("customerId") Long customerId) {
        log.info("Wallet Search Request: {}, customerId: {}", walletSearchRequest.toString(), customerId);
        WalletSearchResult walletSearchResult = walletService.search(walletSearchRequest.toModel(customerId));
        List<WalletResponse> walletResponseList = walletSearchResult.getWallets()
                .stream()
                .map(WalletResponse::fromModel)
                .toList();
        return respond(walletResponseList, walletSearchResult.getPage(), walletSearchResult.getSize(), walletSearchResult.getTotalCount());
    }

    @PostMapping("/create")
    public Response<WalletResponse> create(@Valid @RequestBody WalletCreateRequest walletCreateRequest, @RequestHeader("customerId") Long customerId) {
        Wallet wallet = walletService.create(walletCreateRequest.toModel(customerId));
        return respond(WalletResponse.fromModel(wallet));
    }
}
