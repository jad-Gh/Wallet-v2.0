package com.finance.walletV2.FinTransaction;

import com.finance.walletV2.FinCategory.FinCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinTransactionRepository extends JpaRepository<FinTransaction,Long> {

    Page<FinTransaction> findAllByAppUser_Email(String email,Pageable pageable);

    Optional<FinTransaction> findByIdAndAppUser_Email(Long id,String email);

}
