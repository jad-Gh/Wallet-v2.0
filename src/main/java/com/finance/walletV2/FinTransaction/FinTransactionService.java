package com.finance.walletV2.FinTransaction;

import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.AppUser.AppUserRepository;
import com.finance.walletV2.AppUser.AppUserService;
import com.finance.walletV2.FinCategory.FinCategory;
import com.finance.walletV2.FinCategory.FinCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
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
            log.error("Error Adding Transaction " + e.getMessage());
            throw new RuntimeException("Error Adding Transaction "+ e.getLocalizedMessage());
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

    public Map<String,Object> getTransactions(int page, int size, LocalDate startDate,LocalDate endDate,Long categoryId,int sortFilter){
        //GET current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());
        //convert localDate to localDateTime
        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23,59,59));
        //check valid date
        if (end.isBefore(start)){
            throw new IllegalStateException("Start Date has to be before end Date");
        }
        //generate appropriate sort by filter
        String[] sortFilters = {"createdAt ASC","createdAt DSC","amount ASC","amount DSC"};
        String selectedFilter = sortFilters[sortFilter - 1];

        Page<FinTransaction> data = categoryId==null ?
                finTransactionRepository.findAllByAppUser_EmailAndCreatedAtBetween(
                        appUser.getEmail(),
                        start,
                        end,
                        PageRequest.of(
                                page,
                                size,
//                                Sort.by("createdAt").descending()
                                 Sort.by(selectedFilter.split(" ")[1].equals("ASC") ?
                                        Sort.Direction.ASC :
                                        Sort.Direction.DESC
                                        ,
                                        selectedFilter.split(" ")[0]
                                        )
                        )
                )
        :
                finTransactionRepository.findAllByAppUser_EmailAndFinCategory_IdAndCreatedAtBetween(
                appUser.getEmail(),
                categoryId,
                start,
                end,
                PageRequest.of(
                        page,
                        size,
                        //Sort.by("createdAt").descending()
                        Sort.by(selectedFilter.split(" ")[1].equals("ASC") ?
                                        Sort.Direction.ASC :
                                        Sort.Direction.DESC
                                ,
                                selectedFilter.split(" ")[0]
                        )
                )
                );

        Map<String,Object> result = new HashMap<>();

        result.put("page",data.getNumber());
        result.put("count",data.getNumberOfElements());
        result.put("totalCount",data.getTotalElements());
        result.put("totalPages",data.getTotalPages());
        result.put("data",data.getContent());

        return result;
    }

    public KpiRepresentation getTransactionKpi(Long categoryId, LocalDate startDate,LocalDate endDate){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23,59,59));

        if (end.isBefore(start)){
            throw new IllegalStateException("Start Date has to be before end Date");
        }

        KpiRepresentation kpiRepresentation =
                finTransactionRepository
                .getKpiRepresentation(appUser.getEmail(),categoryId,start,end);
        return kpiRepresentation;
    }

    public List<ChartRepresentation> getTransactionChart(Long categoryId, LocalDate startDate, LocalDate endDate, String periodical){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23,59,59));

        if (end.isBefore(start)){
            throw new IllegalStateException("Start Date has to be before end Date");
        }

        List<ChartRepresentation> result = periodical.equals("DAY") ?
                finTransactionRepository.getChartRepresentationDay(
                appUser.getEmail(),categoryId,start,end
                )
                :
                periodical.equals("MONTH") ?
                        finTransactionRepository.getChartRepresentationMonth(
                                appUser.getEmail(),categoryId,start,end
                        )
                        :
                        finTransactionRepository.getChartRepresentationYear(
                                appUser.getEmail(),categoryId,start,end
                        );
        return result;
    }

    public void deleteTransaction(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        FinTransaction oldFinTransaction = finTransactionRepository.findByIdAndAppUser_Email(id, appUser.getEmail())
                .orElseThrow(()->new NotFoundException("Error Deleting transaction with id %s for user %s".formatted(id,appUser.getEmail())));

        finTransactionRepository.deleteById(id);

    }
}
