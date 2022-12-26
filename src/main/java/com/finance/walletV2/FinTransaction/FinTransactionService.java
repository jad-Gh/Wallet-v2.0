package com.finance.walletV2.FinTransaction;

import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.AppUser.AppUserRepository;
import com.finance.walletV2.AppUser.AppUserService;
import com.finance.walletV2.FinCategory.FinCategory;
import com.finance.walletV2.FinCategory.FinCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class FinTransactionService {

    private final FinTransactionRepository finTransactionRepository;
    private final AppUserService appUserService;
    private final FinCategoryService finCategoryService;

    public void addTransaction(FinTransaction finTransaction){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AppUser appUser = appUserService.getOneUser(auth.getName());

            FinCategory finCategory = finCategoryService.getCategoryByIdAndUser(finTransaction.getFinCategory().getId(), appUser.getEmail());

            finTransaction.setAppUser(appUser);
            finTransaction.setFinCategory(finCategory);

            if (finCategory.isExpense()){
                finTransaction.setAmount( - Math.abs(finTransaction.getAmount()));
            } else {
                finTransaction.setAmount( Math.abs(finTransaction.getAmount()));
            }

            finTransactionRepository.save(finTransaction);

        }catch(Exception e){
            log.error("Error Adding Transaction" + e.getMessage());
            throw new RuntimeException("Error Adding Transaction"+ e.getLocalizedMessage());
        }

    }

    public void updateTransaction(FinTransaction finTransaction){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AppUser appUser = appUserService.getOneUser(auth.getName());

            FinTransaction oldFinTransaction = finTransactionRepository.findByIdAndAppUser_Email(finTransaction.getId(), appUser.getEmail())
                    .orElseThrow(()->new NotFoundException(" with id %s for user %s".formatted(finTransaction.getId(),appUser.getEmail())));

            FinCategory finCategory = finCategoryService.getCategoryByIdAndUser(finTransaction.getFinCategory().getId(), appUser.getEmail());

            oldFinTransaction.setFinCategory(finCategory);
            oldFinTransaction.setDescription(finTransaction.getDescription());

            if (finCategory.isExpense()){
                oldFinTransaction.setAmount( - Math.abs(finTransaction.getAmount()));
            } else {
                oldFinTransaction.setAmount( Math.abs(finTransaction.getAmount()));
            }

            finTransactionRepository.save(oldFinTransaction);

        }catch(Exception e){
            log.error("Error Updating Transaction " + e.getMessage());
            throw new RuntimeException("Error Updating Transaction "+ e.getLocalizedMessage());
        }
    }

    public Map<String,Object> getTransactions(int page, int size){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        Page<FinTransaction> data = finTransactionRepository.findAllByAppUser_Email(appUser.getEmail(), PageRequest.of(page,size, Sort.by("createdAt").descending()));

        Map<String,Object> result = new HashMap<>();

        result.put("page",data.getNumber());
        result.put("count",data.getNumberOfElements());
        result.put("totalCount",data.getTotalElements());
        result.put("totalPages",data.getTotalPages());
        result.put("data",data.getContent());

        return result;
    }

    public KpiRepresentation getTransactionKpi(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        KpiRepresentation kpiRepresentation = finTransactionRepository.getKpiRepresentation(appUser.getEmail());
        return kpiRepresentation;
    }

    public void deleteTransaction(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        FinTransaction oldFinTransaction = finTransactionRepository.findByIdAndAppUser_Email(id, appUser.getEmail())
                .orElseThrow(()->new NotFoundException("Error Deleting transaction with id %s for user %s".formatted(id,appUser.getEmail())));

        finTransactionRepository.deleteById(id);

    }
}
