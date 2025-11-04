package ing.Digital.Wallet.wallet_tx.service.model;

public enum TransactionType {
    DEPOSIT(1),
    WITHDRAW(2);

    private final Integer code;

    TransactionType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
