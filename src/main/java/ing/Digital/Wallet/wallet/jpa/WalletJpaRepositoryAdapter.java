package ing.Digital.Wallet.wallet.jpa;

import ing.Digital.Wallet.currency.jpa.entity.CurrencyEntity;
import ing.Digital.Wallet.currency.jpa.repository.CurrencyJpaRepository;
import ing.Digital.Wallet.currency.jpa.repository.CurrencyRepository;
import ing.Digital.Wallet.customer.jpa.adapter.CustomerJpaRepositoryAdapter;
import ing.Digital.Wallet.customer.jpa.entity.CustomerEntity;
import ing.Digital.Wallet.customer.jpa.repository.CustomerJpaRepository;
import ing.Digital.Wallet.customer.jpa.repository.CustomerRepository;
import ing.Digital.Wallet.wallet.controller.request.WalletSearchRequest;
import ing.Digital.Wallet.wallet.jpa.entity.WalletEntity;
import ing.Digital.Wallet.wallet.jpa.repository.WalletJpaRepository;
import ing.Digital.Wallet.wallet.service.model.BalanceChange;
import ing.Digital.Wallet.wallet.service.model.Wallet;
import ing.Digital.Wallet.wallet.service.model.WalletCreate;
import ing.Digital.Wallet.wallet.service.model.WalletSearch;
import ing.Digital.Wallet.wallet.service.model.WalletSearchResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WalletJpaRepositoryAdapter {
    private final EntityManager entityManager;
    private final CustomerRepository customerRepository;
    private final CurrencyRepository currencyRepository;
    private final WalletJpaRepository walletJpaRepository;

    @Transactional(readOnly = true)
    public WalletSearchResult search(WalletSearch walletSearch) {
        Long totalSize = retrieveTotalSize(walletSearch);
        List<Wallet> wallets = List.of();

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
        walletEntity.setWalletName(walletCreate.getName());
        walletEntity.setBalance(BigDecimal.ZERO);
        walletEntity.setIsActiveForShopping(walletCreate.getIsActiveForShopping());
        walletEntity.setIsActiveForWithdraw(walletCreate.getIsActiveForWithdraw());
        walletEntity.setCustomerEntity(customerEntity);
        walletEntity.setCurrencyEntity(currencyEntity);
        return walletJpaRepository.save(walletEntity).toModel();
    }

    public Wallet upsertBalance(BalanceChange balanceChange) {
        return walletJpaRepository.updateBalanceAmount(balanceChange.getWalletId(),balanceChange.getAmount(),balanceChange.getUsableBalanceAmount()).toModel();
    }

    private List<Wallet> retrieveResults(WalletSearch walletSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Wallet> query = criteriaBuilder.createQuery(Wallet.class);
        Root<WalletEntity> walletEntityRoot = query.from(WalletEntity.class);
        query.where(prepareSearchPredicate(criteriaBuilder, walletEntityRoot, walletSearch));
        query.groupBy(walletEntityRoot.get("id"));

        return entityManager.createQuery(query)
                .setFirstResult(walletSearch.getPage() * walletSearch.getSize())
                .setMaxResults(walletSearch.getSize())
                .getResultList();
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
}
