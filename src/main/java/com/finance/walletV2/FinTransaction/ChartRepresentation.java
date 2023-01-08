package com.finance.walletV2.FinTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChartRepresentation {
    private String date;
    private long transactionCount;
    private double totalVolume;
}
