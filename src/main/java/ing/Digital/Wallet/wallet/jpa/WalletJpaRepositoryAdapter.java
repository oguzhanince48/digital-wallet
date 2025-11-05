package ing.Digital.Wallet.wallet.jpa;

import ing.Digital.Wallet.currency.jpa.entity.CurrencyEntity;
import ing.Digital.Wallet.currency.jpa.repository.CurrencyRepository;
import ing.Digital.Wallet.customer.jpa.entity.CustomerEntity;
import ing.Digital.Wallet.customer.jpa.repository.CustomerRepository;
import ing.Digital.Wallet.wallet.jpa.entity.WalletEntity;
import ing.Digital.Wallet.wallet.jpa.repository.WalletRepository;
import ing.Digital.Wallet.wallet.service.model.BalanceChange;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import ing.Digital.Wallet.wallet.service.model.WalletCreate;
import ing.Digital.Wallet.wallet.service.model.WalletSearch;
import ing.Digital.Wallet.wallet.service.model.WalletSearchResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class WalletJpaRepositoryAdapter {
    private final EntityManager entityManager;
    private final CustomerRepository customerRepository;
    private final CurrencyRepository currencyRepository;
    private final WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public WalletSearchResult search(WalletSearch walletSearch) {
        Long totalSize = retrieveTotalSize(walletSearch);
        List<Wallet> wallets = new ArrayList<>();

        if (totalSize > 0) {
            wallets = retrieveResults(walletSearch);
        }
        return WalletSearchResult.builder()
                .wallets(wallets)
                .totalCount(totalSize)
                .page(walletSearch.getPage())
                .size(walletSearch.getSize())
                .build();
    }

    @Transactional
    public Wallet create(WalletCreate walletCreate) {
        CustomerEntity customerEntity = customerRepository.findById(walletCreate.getCustomerId());
        CurrencyEntity currencyEntity = currencyRepository.findByCode(walletCreate.getCurrencyCode().name());

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setCreatedDate(LocalDateTime.now());
        walletEntity.setWalletName(walletCreate.getName());
        walletEntity.setBalance(BigDecimal.ZERO);
        walletEntity.setUsableBalance(BigDecimal.ZERO);
        walletEntity.setIsActiveForShopping(walletCreate.getIsActiveForShopping());
        walletEntity.setIsActiveForWithdraw(walletCreate.getIsActiveForWithdraw());
        walletEntity.setCustomerEntity(customerEntity);
        walletEntity.setCurrencyEntity(currencyEntity);
        return walletRepository.save(walletEntity).toModel();
    }

    public void upsertBalance(BalanceChange balanceChange) {
        CustomerEntity customerEntity = customerRepository.findById(balanceChange.getCustomerId());
        WalletEntity walletEntity = walletRepository.retrieveByIdAndCustomer(balanceChange.getWalletId(), customerEntity);
        walletRepository.updateBalanceAmount(walletEntity.getId(),balanceChange.getAmount(),balanceChange.getUsableBalanceAmount());
    }

    public Wallet retrieve(Long walletId, Long customerId) {
        CustomerEntity customerEntity = retrieveCustomerEntity(customerId);
        WalletEntity walletEntity = walletRepository.retrieveByIdAndCustomer(walletId, customerEntity);
        return walletEntity.toModel();
    }

    private List<Wallet> retrieveResults(WalletSearch walletSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WalletEntity> query = criteriaBuilder.createQuery(WalletEntity.class);
        Root<WalletEntity> walletEntityRoot = query.from(WalletEntity.class);
        query.where(prepareSearchPredicate(criteriaBuilder, walletEntityRoot, walletSearch));
        return entityManager.createQuery(query)
                .setFirstResult(walletSearch.getPage() * walletSearch.getSize())
                .setMaxResults(walletSearch.getSize())
                .getResultList()
                .stream().map(WalletEntity::toModel).collect(Collectors.toList());
    }

    private Long retrieveTotalSize(WalletSearch walletSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> queryForCount = criteriaBuilder.createQuery(Long.class);
        Root<WalletEntity> rootForCount = queryForCount.from(WalletEntity.class);

        queryForCount
                .select(criteriaBuilder.countDistinct(rootForCount.get("id")));
        queryForCount.where(prepareSearchPredicate(criteriaBuilder, rootForCount, walletSearch));

        return entityManager.createQuery(queryForCount).getSingleResult();
    }

    private Predicate[] prepareSearchPredicate(CriteriaBuilder criteriaBuilder, Root<WalletEntity> walletEntityRoot, WalletSearch walletSearch) {
        return new WalletSearchPredicate(criteriaBuilder, walletEntityRoot)
                .customerId(walletSearch.getCustomerId())
                .currencyCode(walletSearch.getCurrencyCode().name())
                .minAmount(walletSearch.getMinAmount())
                .maxAmount(walletSearch.getMaxAmount())
                .buildArray();
    }

    private CustomerEntity retrieveCustomerEntity(Long customerId) {
        return customerRepository.findById(customerId);
    }
}
