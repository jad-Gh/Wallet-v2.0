package com.finance.walletV2.FinCategory;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinCategoryRepository extends JpaRepository<FinCategory,Long> {

    Page<FinCategory> findAllByNameIgnoreCaseContainingAndAppUser_Email(String name,String email, Pageable pageable);

}
