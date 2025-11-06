package ing.Digital.Wallet.wallet_tx.controller;

import ing.Digital.Wallet.common.rest.BaseController;
import ing.Digital.Wallet.wallet.controller.response.model.DataResponse;
import ing.Digital.Wallet.wallet.controller.response.model.Response;
import ing.Digital.Wallet.wallet_tx.controller.request.WalletDepositRequest;
import ing.Digital.Wallet.wallet_tx.controller.request.WalletTxApprovalRequest;
import ing.Digital.Wallet.wallet_tx.controller.request.WalletTxSearchRequest;
import ing.Digital.Wallet.wallet_tx.controller.request.WalletWithdrawRequest;
import ing.Digital.Wallet.wallet_tx.controller.response.WalletTxSearchResponse;
import ing.Digital.Wallet.wallet_tx.service.WalletTxFacade;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearchResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/admin/v1/transactions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class WalletTxAdminController extends BaseController {

    private final WalletTxFacade walletTxFacade;

    @GetMapping
    public Response<DataResponse<WalletTxSearchResponse>> search(@RequestHeader("customerId") Long customerId, @Valid WalletTxSearchRequest walletTxSearchRequest) {
        log.info("Wallet Transaction By Admin, Search Request: {}, customerId: {}", walletTxSearchRequest.toString(), customerId);
        WalletTxSearchResult walletTxSearchResult = walletTxFacade.search(walletTxSearchRequest.toModel(customerId));
        List<WalletTxSearchResponse> walletTxSearchResponseList = walletTxSearchResult.getWalletTxList().stream()
                .map(WalletTxSearchResponse::fromModel)
                .collect(Collectors.toList());
        return respond(walletTxSearchResponseList, walletTxSearchResult.getPage(), walletTxSearchResult.getSize(), walletTxSearchResult.getTotalCount());
    }

    @PostMapping("/deposit")
    public Response<WalletTx> deposit(@RequestHeader("customerId") Long customerId, @Valid @RequestBody WalletDepositRequest walletDepositRequest) {
        log.info("Wallet Deposit By Admin, Request: {}, customerId: {}", walletDepositRequest.toString(), customerId);
        WalletTx walletTx = walletTxFacade.deposit(walletDepositRequest.toModel(customerId));
        return respond(walletTx);
    }

    @PostMapping("/withdraw")
    public Response<WalletTx> withdraw(@RequestHeader("customerId") Long customerId, @Valid @RequestBody WalletWithdrawRequest walletWithdrawRequest) {
        log.info("Wallet Withdraw By Admin, Request: {}, customerId: {}", walletWithdrawRequest.toString(), customerId);
        WalletTx walletTx = walletTxFacade.withdraw(walletWithdrawRequest.toModel(customerId));
        return respond(walletTx);
    }

    @PostMapping("/approve")
    public Response<WalletTx> approve(@RequestHeader("customerId") Long customerId, @Valid @RequestBody WalletTxApprovalRequest walletTxApprovalRequest) {
        log.info("Wallet Approval By Admin, Request: {}, customerId: {}", walletTxApprovalRequest.toString(), customerId);
        WalletTx walletTx = walletTxFacade.processApproval(walletTxApprovalRequest.toModel(customerId));
        return respond(walletTx);
    }
}
