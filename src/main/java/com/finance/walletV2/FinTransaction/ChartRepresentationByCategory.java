package com.finance.walletV2.FinTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class ChartRepresentationByCategory {

    private String categoryName;
    private long transactionCount;
    private double totalVolume;

    public ChartRepresentationByCategory(String categoryName,long transactionCount,double totalVolume) {
        this.categoryName = categoryName;
        this.transactionCount = transactionCount;
        this.totalVolume = totalVolume;
    }
}
