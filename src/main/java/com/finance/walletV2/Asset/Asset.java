package com.finance.walletV2.Asset;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finance.walletV2.AppUser.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne()
    @JoinColumn()
    @JsonIgnore
    private AppUser appUser;

}
