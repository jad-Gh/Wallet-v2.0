package com.finance.walletV2.FinConversion;

import com.finance.walletV2.CustomResponse.CustomResponse;
import com.finance.walletV2.FinTransaction.FinTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/conversion")
@AllArgsConstructor
public class FinConversionController {

    private final FinConversionService finConversionService;

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteConversion(@PathVariable(name = "id") Long id ){
        finConversionService.deleteConversion(id);
        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully deleted")
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<CustomResponse> addTransaction(@RequestBody FinConversion finConversion){
        finConversionService.addConversion(finConversion);
        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully added")
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getConversions(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(name = "startDate",defaultValue = "1970-01-01")
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                          @RequestParam(name = "endDate",defaultValue = "#{T(java.time.LocalDate).now()}")
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    )
    {
        Map<String,Object> result = finConversionService.getConversions(startDate,endDate,page,size);
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
    public ResponseEntity<CustomResponse> getConversionKpis(
                                                             @RequestParam(name = "startDate",defaultValue = "1970-01-01")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                             @RequestParam(name = "endDate",defaultValue = "#{T(java.time.LocalDate).now()}")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        Map<String,Object> result = new HashMap<>();
        result.put("data",finConversionService.getConversionKpi(startDate,endDate));
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
    public ResponseEntity<CustomResponse> getConversionCharts(
                                                               @RequestParam(name = "startDate",defaultValue = "1970-01-01")
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                               @RequestParam(name = "endDate",defaultValue = "#{T(java.time.LocalDate).now()}")
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                               @RequestParam(name="periodical",defaultValue = "YEAR") String periodical
    ){
        Map<String,Object> result = new HashMap<>();
        result.put("data",finConversionService.getConversionChart(startDate,endDate,periodical));
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
}
