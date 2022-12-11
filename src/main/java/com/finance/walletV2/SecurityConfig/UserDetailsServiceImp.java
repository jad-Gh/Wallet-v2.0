package com.finance.walletV2.SecurityConfig;

import com.finance.walletV2.AppUser.AppUser;
import com.finance.walletV2.AppUser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User with email %s not found during security".formatted(username)));
        return new UserDetailsImp(appUser);
    }
}
