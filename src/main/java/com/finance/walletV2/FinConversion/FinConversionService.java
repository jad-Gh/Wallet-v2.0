package com.finance.walletV2.FinConversion;

import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.AppUser.AppUserService;
import com.finance.walletV2.FinTransaction.ChartRepresentation;
import com.finance.walletV2.FinTransaction.KpiRepresentation;
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
import java.util.List;
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
                PageRequest.of(page,size , Sort.by("createdAt").descending())
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

    public KpiRepresentation getConversionKpi(LocalDate startDate, LocalDate endDate){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23,59,59));

        if (end.isBefore(start)){
            throw new IllegalStateException("Start Date has to be before end Date");
        }

        KpiRepresentation kpiRepresentation =
                finConversionRepository
                        .getKpiRepresentation(appUser.getEmail(),start,end);
        return kpiRepresentation;
    }

    public List<ChartRepresentation> getConversionChart( LocalDate startDate, LocalDate endDate, String periodical){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23,59,59));

        if (end.isBefore(start)){
            throw new IllegalStateException("Start Date has to be before end Date");
        }

        List<ChartRepresentation> result = periodical.equals("DAY") ?
                finConversionRepository.getChartRepresentationDay(
                        appUser.getEmail(),start,end
                )
                :
                periodical.equals("MONTH") ?
                        finConversionRepository.getChartRepresentationMonth(
                                appUser.getEmail(),start,end
                        )
                        :
                        finConversionRepository.getChartRepresentationYear(
                                appUser.getEmail(),start,end
                        );
        return result;
    }

    public FinConversion getActiveConversion(String uerEmail){
        return finConversionRepository.getActiveConversion(uerEmail);
    }

    public long getRemainingLBPSum(String uerEmail){
        return finConversionRepository.getSumRemainingLBP(uerEmail);
    }

    public void updateConversionRemainingBalance(FinConversion finConversion){
        FinConversion oldConv = finConversionRepository.findById(finConversion.getId()).orElseThrow(
                ()->new NotFoundException("Conversion not found id: "+finConversion.getId()));

        oldConv.setRemainingLBP(finConversion.getRemainingLBP());

        finConversionRepository.save(oldConv);
    }




}
