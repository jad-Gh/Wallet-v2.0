package com.finance.walletV2.FinAsset;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@AllArgsConstructor
public class KpiRepresentationAsset {

    private long transactionCount;
    private double totalVolume;
    private double totalProfit;
    private String totalVolumeFormatted;
    private String totalProfitFormatted;

    public KpiRepresentationAsset(long transactionCount, double totalVolume,double totalProfit) {
        this.transactionCount = transactionCount;
        this.totalVolume = totalVolume;
        this.totalProfit = totalProfit;
        this.totalVolumeFormatted = NumberFormat.getCurrencyInstance(new Locale("en","US")).format(totalVolume);
        this.totalProfitFormatted = NumberFormat.getCurrencyInstance(new Locale("en","US")).format(totalProfit);

    }
}
