package com.finance.walletV2.FinCategory;

import com.finance.walletV2.CustomResponse.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping(path = "/category")
@AllArgsConstructor
public class FinCategoryController {

    private final FinCategoryService finCategoryService;

    @GetMapping
    public ResponseEntity<CustomResponse> getCategories(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "") String search)
    {
        Map<String,Object> result = finCategoryService.getCategories(page,size,search);
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
    public ResponseEntity<CustomResponse> addCategory(@RequestBody FinCategory finCategory){
        finCategoryService.addCategory(finCategory);
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
    public ResponseEntity<CustomResponse> updateCategory(@RequestBody FinCategory finCategory){
        finCategoryService.updateCategory(finCategory);
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
    public ResponseEntity<CustomResponse> deleteCategory(@PathVariable(name = "id") Long id ){
        finCategoryService.deleteCategory(id);
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
