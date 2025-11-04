package ing.Digital.Wallet.wallet_tx.jpa;

import ing.Digital.Wallet.wallet_tx.jpa.entity.WalletTxEntity;
import ing.Digital.Wallet.wallet_tx.jpa.repository.WalletTxJpaRepository;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxInfo;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearch;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxSearchResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class WalletTxJpaRepositoryAdapter {

    private final EntityManager entityManager;
    private final WalletTxJpaRepository walletTxJpaRepository;

    public WalletTx save(WalletTxInfo walletTxInfo){
        WalletTxEntity walletTxEntity = new WalletTxEntity();
        walletTxEntity.setAmount(walletTxInfo.getAmount());
        walletTxEntity.setWalletId(walletTxInfo.getWalletId());
        walletTxEntity.setOppositePartyType(walletTxInfo.getOppositePartyType());
        walletTxEntity.setOppositePartyStatus(walletTxInfo.getOppositePartyStatus());
        walletTxEntity.setTransactionType(walletTxInfo.getTransactionType());
        return walletTxJpaRepository.save(walletTxEntity).toModel();
    }

    public WalletTx retrieve(Long id){
        return walletTxJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WalletTx not found"))
                .toModel();
    }

    public WalletTx updateStatus(WalletTx walletTx){
        WalletTxEntity walletTxEntity = walletTxJpaRepository.findById(walletTx.getId())
                .orElseThrow(() -> new RuntimeException("WalletTx not found"));
        walletTxEntity.setOppositePartyStatus(walletTx.getOppositePartyStatus());
        return walletTxJpaRepository.save(walletTxEntity).toModel();
    }

    public WalletTxSearchResult search(WalletTxSearch walletTxSearch){
        Long totalSize = retrieveTotalSize(walletTxSearch);
        List<WalletTx> walletTxList = List.of();

        if (totalSize > 0) {
            walletTxList = retrieveResults(walletTxSearch);
        }
        return WalletTxSearchResult.builder()
                .walletTxList(walletTxList)
                .totalCount(totalSize)
                .page(walletTxSearch.getPage())
                .size(walletTxSearch.getSize())
                .build();
    }

    private List<WalletTx> retrieveResults(WalletTxSearch walletTxSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WalletTx> query = criteriaBuilder.createQuery(WalletTx.class);
        Root<WalletTxEntity> walletEntityRoot = query.from(WalletTxEntity.class);
        query.where(prepareSearchPredicate(criteriaBuilder, walletEntityRoot, walletTxSearch));
        query.groupBy(walletEntityRoot.get("id"));

        return entityManager.createQuery(query)
                .setFirstResult(walletTxSearch.getPage() * walletTxSearch.getSize())
                .setMaxResults(walletTxSearch.getSize())
                .getResultList();
    }

    private Long retrieveTotalSize(WalletTxSearch walletTxSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> queryForCount = criteriaBuilder.createQuery(Long.class);
        Root<WalletTxEntity> rootForCount = queryForCount.from(WalletTxEntity.class);

        queryForCount
                .select(criteriaBuilder.countDistinct(rootForCount.get("id")));
        queryForCount.where(prepareSearchPredicate(criteriaBuilder, rootForCount, walletTxSearch));

        return entityManager.createQuery(queryForCount).getSingleResult();
    }

    private Predicate[] prepareSearchPredicate(CriteriaBuilder criteriaBuilder, Root<WalletTxEntity> walletEntityRoot, WalletTxSearch walletTxSearch) {
        return new WalletTxSearchPredicate(criteriaBuilder, walletEntityRoot)
                .walletId(walletTxSearch.getWalletId())
                .buildArray();
    }
}
