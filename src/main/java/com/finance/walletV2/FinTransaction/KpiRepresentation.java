package com.finance.walletV2.FinTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@AllArgsConstructor
public class KpiRepresentation{

    private long transactionCount;
    private double totalVolume;
    private String totalVolumeFormatted;

    public KpiRepresentation(long transactionCount,double totalVolume) {
        this.transactionCount = transactionCount;
        this.totalVolume = totalVolume;
        this.totalVolumeFormatted = NumberFormat.getCurrencyInstance(new Locale("en","US")).format(totalVolume);
    }
}
