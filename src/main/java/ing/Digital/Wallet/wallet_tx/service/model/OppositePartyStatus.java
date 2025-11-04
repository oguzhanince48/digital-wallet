package ing.Digital.Wallet.wallet_tx.service.model;

public enum OppositePartyStatus {
    PENDING(1),
    APPROVED(2),
    DENIED(3);
    private final Integer code;

    OppositePartyStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
