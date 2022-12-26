package com.finance.walletV2.FinTransaction;

import com.finance.walletV2.FinCategory.FinCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinTransactionRepository extends JpaRepository<FinTransaction,Long> {

    Page<FinTransaction> findAllByAppUser_Email(String email,Pageable pageable);

    Optional<FinTransaction> findByIdAndAppUser_Email(Long id,String email);

    @Query(value = "SELECT new com.finance.walletV2.FinTransaction.KpiRepresentation(COALESCE(COUNT(f),0),COALESCE(SUM(f.amount),0)) FROM FinTransaction f WHERE f.appUser.email=?1")
    KpiRepresentation getKpiRepresentation(String email);

}
