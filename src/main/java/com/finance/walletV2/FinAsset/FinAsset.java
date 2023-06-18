package com.finance.walletV2.FinAsset;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.Asset.Asset;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FinAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Min(value = 0, message = "Price Bought can't be less than zero")
    private double priceBought;

    private String description;

    @Transient
    private String profit;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn()
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne
    @JoinColumn
    private Asset asset;

    @PostLoad
    public void setProfit(){
        this.profit = String.format("%.2f", this.asset.getPriceCurrent() - this.priceBought);
    }
}
