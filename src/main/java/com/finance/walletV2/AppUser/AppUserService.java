package com.finance.walletV2.AppUser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public Map<String, Object> getUsers (int page,int size,String search){
        Page<AppUser> data = appUserRepository.findByEmailContaining(search, PageRequest.of(page,size, Sort.by("createdAt").descending()));
        Map<String,Object> result = new HashMap<>();
        result.put("page",data.getNumber());
        result.put("count",data.getNumberOfElements());
        result.put("totalCount",data.getTotalElements());
        result.put("totalPages",data.getTotalPages());
        result.put("data",data.getContent());
        return result;
    }

    public void addUser (AppUser appUser){
        appUser.setActive(true);
        appUser.setCreatedAt(LocalDateTime.now());
        appUser.setRoleName("ROLE_USER");

        try{
            appUserRepository.save(appUser);
            log.info("User with email: %s added successfully!".formatted(appUser.getEmail()));
        }catch(Exception e){
            log.error("Error adding User with email: %s in Add User Service".formatted(appUser.getEmail()));
            log.error(e.getMessage());
            throw new RuntimeException("Error adding user in Add User Service, Error: " + e.getLocalizedMessage());
        }
    }

    public void updateUser (AppUser appUser){
        appUserRepository.findById(appUser.getId()).orElseThrow(
                ()->{
                    log.error("User with id: %s not found in Update User Service".formatted(appUser.getId()));
                    return new NotFoundException("User with id: %s not found".formatted(appUser.getId()));
                });

        try{
            appUserRepository.save(appUser);
            log.info("User with id: %s updated successfully!".formatted(appUser.getId()));
        }catch(Exception e){
            log.error("Error adding User with id: %s in Add User Service".formatted(appUser.getId()));
            log.error(e.getMessage());
            throw new RuntimeException("Error adding user in Add User Service, Error: " + e.getLocalizedMessage());
        }
    }

    public void deleteUser (Long id){
       appUserRepository.findById(id).orElseThrow(
               ()->{
                   log.error("User with id: %s not found in Delete User Service".formatted(id));
                   return new NotFoundException("User with id: %s not found".formatted(id));
               });

       try {
           appUserRepository.deleteById(id);
           log.info("User with id: %s deleted successfully!".formatted(id));
       }catch (Exception e){
           log.error("Error deleting User with id: %s in Delete User Service".formatted(id));
           log.error(e.getMessage());
           throw new RuntimeException("Error deleting user in Delete User Service, Error: " + e.getLocalizedMessage());
       }
    }






}
