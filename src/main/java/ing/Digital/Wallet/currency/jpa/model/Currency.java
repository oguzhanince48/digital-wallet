package ing.Digital.Wallet.currency.jpa.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Currency {
    private Long id;
    private String code;
}
