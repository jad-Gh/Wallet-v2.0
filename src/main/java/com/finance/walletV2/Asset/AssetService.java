package com.finance.walletV2.Asset;

import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AppUserService appUserService;
    private final RestTemplate restTemplate;

    public List<Asset> findAllAssets(){
        return assetRepository.findAll();
    }

    public void addAsset(Asset asset){
        Asset assetToAdd = new Asset();

        assetToAdd.setName(asset.getName());

        assetRepository.save(assetToAdd);
    }

    public void updateAsset(Asset asset){

        Asset assetToEdit = assetRepository.findById(asset.getId())
                .orElseThrow(()->
                   new NotFoundException("Asset not found")
                );

        assetToEdit.setName(asset.getName());

        assetRepository.save(assetToEdit);
    }

    public void deleteAsset(Long id){

        Asset assetToDelete = assetRepository.findById(id)
                .orElseThrow(()->
                     new NotFoundException("Asset not found")
                );

        assetRepository.deleteById(assetToDelete.getId());
    }

    public void updateGoldPrice(Long id){

        Asset assetToEdit = assetRepository.findById(id)
                .orElseThrow(()->
                        new NotFoundException("Asset not found")
                );

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("x-access-token","goldapi-1ekcnhrlhxi1yg0-io");
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        GoldRepresentation rep = restTemplate.exchange("https://www.goldapi.io/api/XAU/USD",
                HttpMethod.GET, entity, GoldRepresentation.class).getBody();

        assetToEdit.setPriceCurrent(rep.getPrice());
        assetRepository.save(assetToEdit);

    }

}
