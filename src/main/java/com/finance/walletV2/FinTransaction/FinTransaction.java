package com.finance.walletV2.FinTransaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.FinCategory.FinCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private double amount;

    private String description;

    @ManyToOne()
    @JoinColumn
    private FinCategory finCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private AppUser appUser;

}
