package com.finance.walletV2.AppUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.walletV2.FinAsset.FinAsset;
import com.finance.walletV2.FinCategory.FinCategory;
import com.finance.walletV2.FinTransaction.FinTransaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Size(min = 6,message = "Must be longer than 6 characters")
    private String fullName;

    @Column(unique = true,nullable = false)
    @Email(message = "Email should be a valid Email")
    private String email;

    @Column(nullable = false)
    @Size(min = 6,message = "Must be longer than 6 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false,updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String roleName;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false,updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<FinTransaction> finTransactionList;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<FinCategory> finCategoryList;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<FinAsset> finAssetList;

}
