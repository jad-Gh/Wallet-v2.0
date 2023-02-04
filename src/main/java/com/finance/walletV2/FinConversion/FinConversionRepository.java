package com.finance.walletV2.FinConversion;


import com.finance.walletV2.FinTransaction.FinTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FinConversionRepository extends JpaRepository<FinConversion,Long> {

    Page<FinConversion> findAllByAppUser_EmailAndCreatedAtBetween(String email,
                                                                  LocalDateTime startDate,
                                                                  LocalDateTime endDate,
                                                                  Pageable pageable);



}
