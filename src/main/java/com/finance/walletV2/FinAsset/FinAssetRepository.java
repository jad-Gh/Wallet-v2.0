package com.finance.walletV2.FinAsset;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinAssetRepository extends JpaRepository<FinAsset,Long> {

    Page<FinAsset> findAllByAppUser_EmailAndName(String email,String name, Pageable pageable);
}
