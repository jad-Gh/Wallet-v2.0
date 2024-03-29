package com.finance.walletV2.AppUser;

import com.finance.walletV2.CustomResponse.CustomResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<CustomResponse> getUsers (@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "") String search
                                                    ){
        Map<String,Object> result = appUserService.getUsers(page, size, search);
        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Success")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .data(result)
                .build());
    }

    @GetMapping(path = "/info")
    public ResponseEntity<CustomResponse> getUserInfo(){
        Map<String,Object> result = appUserService.getUserInfo();
        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("Success")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .data(result)
                .build());
    }

    @PostMapping
    public ResponseEntity<CustomResponse> addUser(@Valid @RequestBody AppUser appUser){
        appUserService.addUser(appUser);
        return ResponseEntity.ok().body(CustomResponse.builder()
                        .message("User created successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .timestamp(LocalDateTime.now())
                .build());
    }

    @PutMapping
    public ResponseEntity<CustomResponse> updateUser(@Valid @RequestBody AppUser appUser){
        appUserService.updateUser(appUser);
        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("User updated successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> deleteUser(@PathVariable Long id){
        appUserService.deleteUser(id);
        return ResponseEntity.ok().body(CustomResponse.builder()
                .message("User deleted successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }
}
