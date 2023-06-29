package com.finance.walletV2.Asset;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.FinAsset.FinAsset;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 1, message = "Please enter a valid name")
    @Column(unique = true)
    private String name;

    private double priceCurrent;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<FinAsset> finAssetList;

}
