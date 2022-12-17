package com.finance.walletV2.FinCategory;

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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class FinCategoryService {

    private final FinCategoryRepository finCategoryRepository;
    private final AppUserService appUserService;

    public void addCategory (FinCategory finCategory){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AppUser appUser = appUserService.getOneUser(auth.getName());
            finCategory.setAppUser(appUser);
            finCategory.setCreatedAt(LocalDateTime.now());

            finCategoryRepository.save(finCategory);
        }catch(Exception e){
            log.error("Error Adding Category" + e.getMessage());
            throw new RuntimeException("Error Adding Category"+ e.getLocalizedMessage());
        }
    }

    public void updateCategory (FinCategory finCategory){
        FinCategory OldCategory = finCategoryRepository.findById(finCategory.getId()).orElseThrow(
                ()->{
                    log.error("Category with id: %s not found in Update Category Service".formatted(finCategory.getId()));
                    return new NotFoundException("Category with id: %s not found".formatted(finCategory.getId()));
                });

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        if (appUser.getId() == OldCategory.getAppUser().getId()){
            try {
                OldCategory.setName(finCategory.getName());
                OldCategory.setExpense(finCategory.isExpense());
                finCategoryRepository.save(OldCategory);
            }catch(Exception e){
                log.error("Error Updating Category" + e.getMessage());
                throw new RuntimeException("Error Updating Category"+ e.getLocalizedMessage());
            }
        } else {
            log.error("Category Does not belong to User " + appUser.getEmail());
            throw new IllegalArgumentException("Category Does not belong to User " + appUser.getEmail());
        }

    }

    public Map<String,Object> getCategories (int page,int size,String search){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        Map<String,Object> result = new HashMap<>();

        Page<FinCategory> data = finCategoryRepository.findAllByNameIgnoreCaseContainingAndAppUser_Email(
                search,
                appUser.getEmail(),
                PageRequest.of(page,size, Sort.by("createdAt").descending())
        );

        result.put("page",data.getNumber());
        result.put("count",data.getNumberOfElements());
        result.put("totalCount",data.getTotalElements());
        result.put("totalPages",data.getTotalPages());
        result.put("data",data.getContent());

        return result;
    }

    public void deleteCategory (Long id){
        FinCategory OldCategory = finCategoryRepository.findById(id).orElseThrow(
                ()->{
                    log.error("Category with id: %s not found in Delete Category Service".formatted(id));
                    return new NotFoundException("Category with id: %s not found".formatted(id));
                });

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        if (appUser.getId() == OldCategory.getAppUser().getId()){
            try {
                finCategoryRepository.deleteById(id);
            }catch(Exception e){
                log.error("Error Deleting Category" + e.getMessage());
                throw new RuntimeException("Error Deleting Category"+ e.getLocalizedMessage());
            }
        } else {
            log.error("Category Does not belong to User " + appUser.getEmail());
            throw new IllegalArgumentException("Category Does not belong to User " + appUser.getEmail());
        }
    }


}
