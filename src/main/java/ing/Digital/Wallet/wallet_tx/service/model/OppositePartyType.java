package ing.Digital.Wallet.wallet_tx.service.model;

public enum OppositePartyType {
    IBAN(1),
    PAYMENT(2);

    private final Integer code;

    OppositePartyType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
