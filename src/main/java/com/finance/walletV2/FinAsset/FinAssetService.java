package com.finance.walletV2.FinAsset;

import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.AppUser.AppUserService;
import com.finance.walletV2.Asset.Asset;
import com.finance.walletV2.Asset.AssetRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
public class FinAssetService {

    private final FinAssetRepository finAssetRepository;
    private final AssetRepository assetRepository;
    private final AppUserService appUserService;

    public Map<String,Object> getFinAssets(int page,int size){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        Page<FinAsset> data = finAssetRepository.findAllByAppUser_Email(
                appUser.getEmail(), PageRequest.of(page,size, Sort.by("createdAt").descending())
        );

        Map<String,Object> result = new HashMap<>();

        result.put("page",data.getNumber());
        result.put("count",data.getNumberOfElements());
        result.put("totalCount",data.getTotalElements());
        result.put("totalPages",data.getTotalPages());
        result.put("data",data.getContent());

        return result;
    }

    public void addFinAsset(FinAsset finAsset){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        Asset assetToRetrieve = assetRepository.findByIdAndAppUser_Email(finAsset.getAsset().getId(), appUser.getEmail())
                .orElseThrow(()->
                        new NotFoundException("Asset not found")
                );

        FinAsset finAssetToAdd = new FinAsset();

        finAssetToAdd.setAsset(assetToRetrieve);
        finAssetToAdd.setAppUser(appUser);
        finAssetToAdd.setPriceBought(finAsset.getPriceBought());
        finAssetToAdd.setDescription(finAsset.getDescription());

        finAssetRepository.save(finAssetToAdd);
    }

    public void updateFinAsset(FinAsset finAsset){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        FinAsset finAssetToEdit = finAssetRepository.findByIdAndAppUser_Email(finAsset.getAsset().getId(), appUser.getEmail())
                .orElseThrow(()->
                        new NotFoundException("Financial Asset not found")
                );

        Asset assetToRetrieve = assetRepository.findByIdAndAppUser_Email(finAsset.getAsset().getId(), appUser.getEmail())
                .orElseThrow(()->
                        new NotFoundException("Asset not found")
                );

        finAssetToEdit.setAsset(assetToRetrieve);
        finAssetToEdit.setPriceBought(finAsset.getPriceBought());
        finAssetToEdit.setDescription(finAsset.getDescription());

        finAssetRepository.save(finAssetToEdit);
    }

    public void deleteFinAsset(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        FinAsset finAssetToDelete = finAssetRepository.findByIdAndAppUser_Email(id, appUser.getEmail())
                .orElseThrow(()->
                        new NotFoundException("Financial Asset not found")
                );

        finAssetRepository.deleteById(finAssetToDelete.getId());
    }

}
