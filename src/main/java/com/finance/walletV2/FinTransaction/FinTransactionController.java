package com.finance.walletV2.FinTransaction;

import com.finance.walletV2.CustomResponse.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
                                                        @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(name = "startDate",defaultValue = "1970-01-01")
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                          @RequestParam(name = "endDate",defaultValue = "#{T(java.time.LocalDate).now()}")
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                        @RequestParam(required = false) Long categoryId,
                                                          @RequestParam(defaultValue = "2") int orderBy
                                                        )
    {
        Map<String,Object> result = finTransactionService.getTransactions(page,size,startDate,endDate,categoryId,orderBy);
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
    public ResponseEntity<CustomResponse> getTransactionKpis(@RequestParam(name = "id",required = false) Long categoryId,
                                                             @RequestParam(name = "startDate",defaultValue = "1970-01-01")
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                             @RequestParam(name = "endDate",defaultValue = "#{T(java.time.LocalDate).now()}")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
                                                             ){
        Map<String,Object> result = new HashMap<>();
        result.put("data",finTransactionService.getTransactionKpi(categoryId,startDate,endDate));
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

    @GetMapping(path = "/charts")
    public ResponseEntity<CustomResponse> getTransactionCharts(@RequestParam(name = "id",required = false) Long categoryId,
                                                             @RequestParam(name = "startDate",defaultValue = "1970-01-01")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                             @RequestParam(name = "endDate",defaultValue = "#{T(java.time.LocalDate).now()}")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                               @RequestParam(name="periodical",defaultValue = "YEAR") String periodical
    ){
        Map<String,Object> result = new HashMap<>();
        result.put("data",finTransactionService.getTransactionChart(categoryId,startDate,endDate,periodical));
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

    @PostMapping(path = "/lbp")
    public ResponseEntity<CustomResponse> addLBPTransaction(@RequestBody FinTransaction finTransaction){
        finTransactionService.addLbpTransaction(finTransaction);
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
