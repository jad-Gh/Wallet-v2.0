package com.finance.walletV2.FinConversion;

import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class FinConversionService {

    private final FinConversionRepository finConversionRepository;
    private final AppUserService appUserService;

    public Map<String,Object> getConversions(LocalDate startDate, LocalDate endDate,int page,int size){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23,59,59));

        Page<FinConversion> data = finConversionRepository.findAllByAppUser_EmailAndCreatedAtBetween(
                appUser.getEmail(),
                start,
                end,
                PageRequest.of(page,size , Sort.by("createdAt").ascending())
                );

        Map<String,Object> result = new HashMap<>();

        result.put("page",data.getNumber());
        result.put("count",data.getNumberOfElements());
        result.put("totalCount",data.getTotalElements());
        result.put("totalPages",data.getTotalPages());
        result.put("data",data.getContent());

        return result;
    }

    public void addConversion(FinConversion finConversion){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        finConversion.setAmountLBP(finConversion.getRate() * finConversion.getAmountUSD());
        finConversion.setRemainingLBP(finConversion.getRate() * finConversion.getAmountUSD());
        finConversion.setAppUser(appUser);

        try {
            finConversionRepository.save(finConversion);
        } catch(Exception e){
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    public void deleteConversion(Long id){
        finConversionRepository.findById(id).orElseThrow(()->new NotFoundException("Conversion with id: "+id+" not found"));
        try {
            finConversionRepository.deleteById(id);
        } catch(Exception e){
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getMessage());
        }
    }




}
