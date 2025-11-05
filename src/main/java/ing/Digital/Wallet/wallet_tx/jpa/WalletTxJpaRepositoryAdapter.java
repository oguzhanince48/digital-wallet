package ing.Digital.Wallet.wallet_tx.jpa;

import ing.Digital.Wallet.common.exception.WalletApiBusinessException;
import ing.Digital.Wallet.wallet_tx.jpa.entity.WalletTxEntity;
import ing.Digital.Wallet.wallet_tx.jpa.repository.WalletTxJpaRepository;
import ing.Digital.Wallet.wallet_tx.jpa.repository.WalletTxRepository;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTx;
import ing.Digital.Wallet.wallet_tx.service.model.WalletTxApproval;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class WalletTxJpaRepositoryAdapter {

    private final EntityManager entityManager;
    private final WalletTxRepository walletTxRepository;

    public WalletTx save(WalletTxInfo walletTxInfo){
        WalletTxEntity walletTxEntity = new WalletTxEntity();
        walletTxEntity.setCreatedDate(LocalDateTime.now());
        walletTxEntity.setAmount(walletTxInfo.getAmount());
        walletTxEntity.setWalletId(walletTxInfo.getWalletId());
        walletTxEntity.setOppositePartyType(walletTxInfo.getOppositePartyType());
        walletTxEntity.setOppositePartyStatus(walletTxInfo.getOppositePartyStatus());
        walletTxEntity.setTransactionType(walletTxInfo.getTransactionType());
        return walletTxRepository.save(walletTxEntity).toModel();
    }

    public WalletTx retrieve(Long id){
        return walletTxRepository.retrieve(id).toModel();
    }

    public WalletTx updateStatus(WalletTx walletTx, WalletTxApproval walletTxApproval){
        WalletTxEntity walletTxEntity = walletTxRepository.retrieve(walletTx.getId());
        walletTxEntity.setOppositePartyStatus(walletTxApproval.getOppositePartyStatus());
        walletTxEntity.setUpdatedDate(LocalDateTime.now());
        return walletTxRepository.save(walletTxEntity).toModel();
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
        CriteriaQuery<WalletTxEntity> query = criteriaBuilder.createQuery(WalletTxEntity.class);
        Root<WalletTxEntity> walletTxEntityRoot = query.from(WalletTxEntity.class);
        query.where(prepareSearchPredicate(criteriaBuilder, walletTxEntityRoot, walletTxSearch));

        return entityManager.createQuery(query)
                .setFirstResult(walletTxSearch.getPage() * walletTxSearch.getSize())
                .setMaxResults(walletTxSearch.getSize())
                .getResultList().stream().map(WalletTxEntity::toModel).collect(Collectors.toList());
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
