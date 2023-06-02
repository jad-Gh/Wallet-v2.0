package com.finance.walletV2.Asset;


import com.finance.walletV2.CustomResponse.CustomResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/asset")
@AllArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<CustomResponse> findAllAssets(){
        List<Asset> result = assetService.findAllAssets();
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("data",result);
        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Asset fetch successfull")
                .status(HttpStatus.OK)
                .data(toSend)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PostMapping
    public ResponseEntity<CustomResponse> addAsset(@Valid @RequestBody Asset asset){

        assetService.addAsset(asset);

        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Asset added successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PutMapping
    public ResponseEntity<CustomResponse> updateAsset(@Valid @RequestBody Asset asset){

        assetService.updateAsset(asset);

        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Asset updated successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> updateAsset(@PathVariable Long id){

        assetService.deleteAsset(id);

        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Asset deleted successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }
}
