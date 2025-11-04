package ing.Digital.Wallet.common.model;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Status {

    ACTIVE(1),
    DELETED(-1),
    PASSIVE(0);

    private final Integer code;
    Status(Integer code) {
        this.code = code;
    }
}
