package com.finance.walletV2.FinAsset;

import lombok.Data;


@Data
public class ChartRepresentationAsset {

    private String categoryName;
    private long transactionCount;
    private double totalVolume;
    private double totalProfit;

    public ChartRepresentationAsset(String categoryName, long transactionCount, double totalVolume,double totalProfit) {
        this.categoryName = categoryName;
        this.transactionCount = transactionCount;
        this.totalVolume = totalVolume;
        this.totalProfit = totalProfit;
    }
}
