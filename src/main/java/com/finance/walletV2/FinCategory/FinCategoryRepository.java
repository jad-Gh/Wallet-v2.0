package com.finance.walletV2.FinCategory;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinCategoryRepository extends JpaRepository<FinCategory,Long> {

    Page<FinCategory> findAllByNameIgnoreCaseContainingAndAppUser_Email(String name,String email, Pageable pageable);

    Optional<FinCategory> findByIdAndAppUser_Email(Long id,String email);

}
