package com.finance.walletV2.FinTransaction;

import com.finance.walletV2.CustomResponse.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/transaction")
@AllArgsConstructor
public class FinTransactionController {

    private final FinTransactionService finTransactionService;

    @GetMapping
    public ResponseEntity<CustomResponse> getTransactions(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size
                                                        )
    {
        Map<String,Object> result = finTransactionService.getTransactions(page,size);
        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .data(result)
                        .build()
        );
    }

    @GetMapping(path = "/kpis")
    public ResponseEntity<CustomResponse> getTransactionKpis(@RequestParam(name = "id") Long id){
        Map<String,Object> result = new HashMap<>();
        result.put("data",finTransactionService.getTransactionKpi(id));
        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .data(result)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<CustomResponse> addTransaction(@RequestBody FinTransaction finTransaction){
        finTransactionService.addTransaction(finTransaction);
        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully added")
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<CustomResponse> updateTransaction(@RequestBody FinTransaction finTransaction){
        finTransactionService.updateTransaction(finTransaction);
        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully updated")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteTransaction(@PathVariable(name = "id") Long id ){
        finTransactionService.deleteTransaction(id);
        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully deleted")
                        .build()
        );
    }


}
