package ing.Digital.Wallet.customer.jpa.adapter;

import ing.Digital.Wallet.customer.jpa.repository.CustomerRepository;
import ing.Digital.Wallet.customer.service.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerJpaRepositoryAdapter {
    private final CustomerRepository customerRepository;

    public Customer retrieve(Long customerId) {
        return customerRepository.findById(customerId).toModel();
    }
}
