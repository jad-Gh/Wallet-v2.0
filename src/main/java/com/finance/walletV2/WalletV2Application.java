package com.finance.walletV2;

import com.finance.walletV2.SecurityConfig.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class WalletV2Application {

	public static void main(String[] args) {
		SpringApplication.run(WalletV2Application.class, args);
	}

}
