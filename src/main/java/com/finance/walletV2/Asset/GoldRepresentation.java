package com.finance.walletV2.Asset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoldRepresentation {
    private double timestamp;
    private String metal;
    private String currency;
    private String exchange;
    private String symbol;
    private double prev_close_price;
    private double open_price;
    private double low_price;
    private double high_price;
    private double open_time;
    private double price;
    private double ch;
    private double chp;
    private double ask;
    private double bid;
    private double price_gram_24k;
    private double price_gram_22k;
    private double price_gram_21k;
    private double price_gram_20k;
    private double price_gram_18k;
}
