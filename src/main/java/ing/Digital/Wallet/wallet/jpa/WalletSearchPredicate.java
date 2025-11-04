package ing.Digital.Wallet.wallet.jpa;

import ch.qos.logback.core.util.StringUtil;
import ing.Digital.Wallet.currency.jpa.entity.CurrencyEntity;
import ing.Digital.Wallet.currency.jpa.model.CurrencyCode;
import ing.Digital.Wallet.customer.jpa.entity.CustomerEntity;
import ing.Digital.Wallet.wallet.jpa.entity.WalletEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class WalletSearchPredicate {

    private List<Predicate> predicateList;
    private CriteriaBuilder criteriaBuilder;
    private Root<WalletEntity> root;
    private Join<WalletEntity, CustomerEntity> customerEntityJoin;
    private Join<WalletEntity, CurrencyEntity> currencyEntityJoin;

    WalletSearchPredicate(CriteriaBuilder criteriaBuilder, Root<WalletEntity> root) {
        this.predicateList = new ArrayList<>();
        this.criteriaBuilder = criteriaBuilder;
        this.customerEntityJoin = root.join("customerEntity");
        this.currencyEntityJoin = root.join("currencyEntity");
    }

    WalletSearchPredicate customerId(Long customerId) {
        if (Objects.nonNull(customerId)) {
            predicateList.add(criteriaBuilder.equal(customerEntityJoin.get("id"), customerId));
        }
        return this;
    }

    WalletSearchPredicate currencyCode(String currencyCode) {
        if (Objects.nonNull(currencyCode) && !StringUtils.isEmpty(currencyCode)) {
            predicateList.add(criteriaBuilder.equal(currencyEntityJoin.get("code"), currencyCode));
        }
        return this;
    }

    WalletSearchPredicate minAmount(BigDecimal minAmount) {
        if (Objects.nonNull(minAmount)) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("balance"), minAmount));
        }
        return this;
    }

    WalletSearchPredicate maxAmount(BigDecimal maxAmount) {
        if (Objects.nonNull(maxAmount)) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("balance"), maxAmount));
        }
        return this;
    }

    Predicate[] buildArray() {
        return predicateList.toArray(new Predicate[0]);
    }
}
