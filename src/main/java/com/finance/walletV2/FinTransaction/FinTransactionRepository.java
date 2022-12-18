package com.finance.walletV2.FinTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinTransactionRepository extends JpaRepository<FinTransaction,Long> {

}
