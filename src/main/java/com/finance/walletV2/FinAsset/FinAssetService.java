package com.finance.walletV2.FinAsset;

import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class FinAssetService {

    private final FinAssetRepository finAssetRepository;
    private final AppUserService appUserService;

    public Map<String,Object> getFinAssets(int page,int size,String search){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        Page<FinAsset> data = finAssetRepository.findAllByAppUser_EmailAndName(
                appUser.getEmail(), search, PageRequest.of(page,size, Sort.by("createdAt").descending())
        );

        Map<String,Object> result = new HashMap<>();

        result.put("page",data.getNumber());
        result.put("count",data.getNumberOfElements());
        result.put("totalCount",data.getTotalElements());
        result.put("totalPages",data.getTotalPages());
        result.put("data",data.getContent());

        return result;
    }
}
