package ing.Digital.Wallet.customer.service.model;

import ing.Digital.Wallet.common.model.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Customer {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Status status;
    private String name;
    private String surname;
    private String identityNumber;
}
