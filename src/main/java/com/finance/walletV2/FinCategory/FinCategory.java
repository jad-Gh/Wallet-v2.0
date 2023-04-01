package com.finance.walletV2.FinCategory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.walletV2.AppUser.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @Column(unique = true)
    @Size(min=1,max=50)
    private String name;

    @Column(nullable = false)
    private boolean expense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    @JsonIgnore
    private AppUser appUser;

}
