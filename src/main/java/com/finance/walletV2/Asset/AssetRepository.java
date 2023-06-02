package com.finance.walletV2.Asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Long> {
    List<Asset> findAllByAppUser_Email(String email);

    Optional<Asset> findByIdAndAppUser_Email(Long id,String email);
}
