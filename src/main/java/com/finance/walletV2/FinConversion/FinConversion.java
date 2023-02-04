package com.finance.walletV2.FinConversion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.walletV2.AppUser.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinConversion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Min(value = 0,message = "USD amount has to be greater than zero")
    private double amountUSD;

    @Column(nullable = false)
    @Min(value = 0,message = "Rate has to be greater than zero")
    private int rate;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double amountLBP;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double remainingLBP;

    @CreationTimestamp
    @Column(updatable = false,nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn()
    @JsonIgnore()
    private AppUser appUser;


}
