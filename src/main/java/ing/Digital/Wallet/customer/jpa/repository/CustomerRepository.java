package ing.Digital.Wallet.customer.jpa.repository;

import ing.Digital.Wallet.customer.jpa.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;

    public CustomerEntity findById(Long customerId) {
        return customerJpaRepository.findById(customerId).orElse(null);  // TODO throw exception
    }
}
