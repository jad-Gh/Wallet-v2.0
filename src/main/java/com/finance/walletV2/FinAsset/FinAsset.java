package com.finance.walletV2.FinAsset;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finance.walletV2.AppUser.AppUser;
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

    @Min(value = 0, message = "Price Current can't be less than zero")
    private double priceCurrent;

    @Column(nullable = false)
    @Size(min = 1, message = "Name can't be less than 1 character")
    private String name;

    private String description;

    @Transient
    private double profit;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn()
    @JsonIgnore
    private AppUser appUser;
}
