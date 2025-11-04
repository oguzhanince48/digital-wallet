package ing.Digital.Wallet.wallet.controller.response.model;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String errorCode;
    private String errorDescription;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

}
