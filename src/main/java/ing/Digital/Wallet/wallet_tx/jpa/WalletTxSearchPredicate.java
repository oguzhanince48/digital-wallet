package ing.Digital.Wallet.wallet_tx.jpa;

import ing.Digital.Wallet.wallet_tx.jpa.entity.WalletTxEntity;
import ing.Digital.Wallet.wallet_tx.service.model.OppositePartyStatus;
import ing.Digital.Wallet.wallet_tx.service.model.TransactionType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WalletTxSearchPredicate {
    private List<Predicate> predicateList;
    private CriteriaBuilder criteriaBuilder;
    private Root<WalletTxEntity> root;

    WalletTxSearchPredicate(CriteriaBuilder criteriaBuilder, Root<WalletTxEntity> root) {
        this.predicateList = new ArrayList<>();
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }

    WalletTxSearchPredicate walletId(Long walletId) {
        if (Objects.nonNull(walletId)) {
            predicateList.add(criteriaBuilder.equal(root.get("walletId"), walletId));
        }
        return this;
    }

    WalletTxSearchPredicate transactionType(TransactionType transactionType) {
        if (Objects.nonNull(transactionType)) {
            predicateList.add(criteriaBuilder.equal(root.get("transactionType"), transactionType));
        }
        return this;
    }

    WalletTxSearchPredicate oppositePartyStatus(OppositePartyStatus oppositePartyStatus) {
        if (Objects.nonNull(oppositePartyStatus)) {
            predicateList.add(criteriaBuilder.equal(root.get("oppositePartyStatus"), oppositePartyStatus));
        }
        return this;
    }

    Predicate[] buildArray() {
        return predicateList.toArray(new Predicate[0]);
    }
}
