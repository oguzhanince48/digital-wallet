package ing.Digital.Wallet.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletApiBusinessException extends RuntimeException {
    private final String key;
    private final String[] args;

    public WalletApiBusinessException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public WalletApiBusinessException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
