package com.finance.walletV2.FinAsset;

import com.finance.walletV2.Asset.Asset;
import com.finance.walletV2.Asset.AssetService;
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
public class FinAssetController {

    private final FinAssetService finAssetService;

    @GetMapping
    public ResponseEntity<CustomResponse> findAllFinAssets(@RequestParam int page, @RequestParam  int size){
        Map<String,Object> toSend = finAssetService.getFinAssets(page, size);
        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Fin Asset fetch successful")
                .status(HttpStatus.OK)
                .data(toSend)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PostMapping
    public ResponseEntity<CustomResponse> addFinAsset(@Valid @RequestBody FinAsset finAsset){

        finAssetService.addFinAsset(finAsset);

        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Fin Asset added successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PutMapping
    public ResponseEntity<CustomResponse> updateFinAsset(@Valid @RequestBody FinAsset finAsset){

        finAssetService.updateFinAsset(finAsset);

        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Fin Asset updated successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> deleteFinAsset(@PathVariable Long id){

        finAssetService.deleteFinAsset(id);

        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Fin Asset deleted successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

}

