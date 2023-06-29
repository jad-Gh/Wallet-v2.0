package com.finance.walletV2.FinAsset;

import com.finance.walletV2.FinTransaction.ChartRepresentationByCategory;
import com.finance.walletV2.FinTransaction.KpiRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FinAssetRepository extends JpaRepository<FinAsset,Long> {

    Page<FinAsset> findAllByAppUser_Email(String email, Pageable pageable);

    Optional<FinAsset> findByIdAndAppUser_Email(Long id,String email);


    @Query(value = "Select new com.finance.walletV2.FinAsset.KpiRepresentationAsset(" +
            "COALESCE(COUNT(f),0),COALESCE(SUM(f.asset.priceCurrent),0),COALESCE(SUM(f.asset.priceCurrent - f.priceBought),0)) " +
            "FROM FinAsset f " +
            "WHERE f.appUser.email = ?1 " +
            "AND f.createdAt >=?2 " +
            "AND f.createdAt <=?3"
    )
    KpiRepresentationAsset findKpiRepresentation(String email, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "Select new com.finance.walletV2.FinAsset.ChartRepresentationAsset(" +
            "f.asset.name,COUNT(f),SUM(f.asset.priceCurrent),SUM(f.asset.priceCurrent - f.priceBought)) " +
            "FROM FinAsset f " +
            "WHERE f.appUser.email = ?1 " +
            "AND f.createdAt >=?2 " +
            "AND f.createdAt <=?3 " +
            "GROUP BY f.asset.name")
    List<ChartRepresentationAsset> findChartRepresentation(String email, LocalDateTime startDate, LocalDateTime endDate);


}
