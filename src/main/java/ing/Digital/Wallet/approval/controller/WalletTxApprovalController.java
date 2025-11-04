package ing.Digital.Wallet.approval.controller;

import ing.Digital.Wallet.approval.controller.request.WalletTxApprovalRequest;
import ing.Digital.Wallet.approval.service.WalletTxApprovalService;
import ing.Digital.Wallet.common.rest.BaseController;
import ing.Digital.Wallet.wallet.controller.response.model.Response;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/wallet/v1/approve")
public class WalletTxApprovalController extends BaseController {

    private final WalletTxApprovalService walletTxApprovalService;

    @PostMapping
    public Response<WalletTx> approve(@Valid WalletTxApprovalRequest walletTxApprovalRequest) {
        WalletTx walletTx = walletTxApprovalService.processApproval(walletTxApprovalRequest.getTransactionId(), walletTxApprovalRequest.getOppositePartyStatus());
        return respond(walletTx);
    }
}
