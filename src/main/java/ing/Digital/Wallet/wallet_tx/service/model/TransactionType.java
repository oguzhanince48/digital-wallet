package ing.Digital.Wallet.wallet_tx.service.model;

import lombok.Builder;
import lombok.Data;


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
