package com.finance.walletV2.Asset;

import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AppUserService appUserService;

    public List<Asset> findAllAssets(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        return assetRepository.findAllByAppUser_Email(appUser.getEmail());
    }

    public void addAsset(Asset asset){
        Asset assetToAdd = new Asset();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        assetToAdd.setName(asset.getName());
        assetToAdd.setAppUser(appUser);

        assetRepository.save(assetToAdd);
    }

    public void updateAsset(Asset asset){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        Asset assetToEdit = assetRepository.findByIdAndAppUser_Email(asset.getId(), appUser.getEmail())
                .orElseThrow(()->
                   new NotFoundException("Asset not found")
                );

        assetToEdit.setName(asset.getName());

        assetRepository.save(assetToEdit);
    }

    public void deleteAsset(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getOneUser(auth.getName());

        Asset assetToDelete = assetRepository.findByIdAndAppUser_Email(id, appUser.getEmail())
                .orElseThrow(()->
                     new NotFoundException("Asset not found")
                );

        assetRepository.deleteById(assetToDelete.getId());
    }


}
