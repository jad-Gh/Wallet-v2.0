package com.finance.walletV2.FinTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KpiRepresentation{

    private long transactionCount;
    private double totalVolume;
}
