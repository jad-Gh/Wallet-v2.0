package com.finance.walletV2.FinConversion;


import com.finance.walletV2.FinTransaction.ChartRepresentation;
import com.finance.walletV2.FinTransaction.FinTransaction;
import com.finance.walletV2.FinTransaction.KpiRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FinConversionRepository extends JpaRepository<FinConversion,Long> {

    Page<FinConversion> findAllByAppUser_EmailAndCreatedAtBetween(String email,
                                                                  LocalDateTime startDate,
                                                                  LocalDateTime endDate,
                                                                  Pageable pageable);

    @Query(value = "SELECT new com.finance.walletV2.FinTransaction.KpiRepresentation(COALESCE(COUNT(f),0),COALESCE(SUM(f.amountUSD),0)) " +
            "FROM FinConversion f WHERE f.appUser.email=?1 " +
            "AND ( f.createdAt>=?2 ) " +
            "AND ( f.createdAt<=?3 ) "
    )
    KpiRepresentation getKpiRepresentation(String email, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT new com.finance.walletV2.FinTransaction.ChartRepresentation" +
            "(function('to_char',f.createdAt,'YYYY'),COUNT(f),SUM(f.amountUSD))" +
            "FROM FinConversion f WHERE f.appUser.email=?1 " +
            "AND ( f.createdAt>=?2 ) " +
            "AND ( f.createdAt<=?3 ) " +
            "GROUP BY function('to_char',f.createdAt,'YYYY') "+
            "ORDER BY function('to_char',f.createdAt,'YYYY') ASC"
    )
    List<ChartRepresentation> getChartRepresentationYear(String email, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT new com.finance.walletV2.FinTransaction.ChartRepresentation" +
            "(function('to_char',f.createdAt,'YYYY-MM'),COUNT(f),SUM(f.amountUSD))" +
            "FROM FinConversion f WHERE f.appUser.email=?1 " +
            "AND ( f.createdAt>=?2 ) " +
            "AND ( f.createdAt<=?3 ) " +
            "GROUP BY function('to_char',f.createdAt,'YYYY-MM') "+
            "ORDER BY function('to_char',f.createdAt,'YYYY-MM') ASC"
    )
    List<ChartRepresentation> getChartRepresentationMonth(String email, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT new com.finance.walletV2.FinTransaction.ChartRepresentation" +
            "(function('to_char',f.createdAt,'YYYY-MM-DD'),COUNT(f),SUM(f.amountUSD))" +
            "FROM FinConversion f WHERE f.appUser.email=?1 " +
            "AND ( f.createdAt>=?2 ) " +
            "AND ( f.createdAt<=?3 ) " +
            "GROUP BY function('to_char',f.createdAt,'YYYY-MM-DD') "+
            "ORDER BY function('to_char',f.createdAt,'YYYY-MM-DD') ASC"
    )
    List<ChartRepresentation> getChartRepresentationDay(String email, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT f FROM FinConversion f WHERE f.createdAt=" +
            "(SELECT min(f2.createdAt) FROM FinConversion f2 WHERE f2.remainingLBP>0)")
    FinConversion getActiveConversion(String email);



}
