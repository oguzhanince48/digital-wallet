package ing.Digital.Wallet.common.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@Getter
public class WalletConfiguration {
    @Value("${wallet.transaction.limit:1000}")
    private BigDecimal limit;
}
