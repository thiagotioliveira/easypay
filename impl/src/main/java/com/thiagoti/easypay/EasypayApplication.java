package com.thiagoti.easypay;

import com.thiagoti.easypay.model.CreateUserDTO;
import com.thiagoti.easypay.model.entity.User.Role;
import com.thiagoti.easypay.user.UserService;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class EasypayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasypayApplication.class, args);
    }

    @Bean(name = "applicationEventMulticaster")
    ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }

    @Bean
    @Profile("!test")
    CommandLineRunner dumpCommandRunner(UserService userService) {
        return args -> {
            var user1 = userService.create(CreateUserDTO.builder()
                    .amount(new BigDecimal("100"))
                    .cpfCnpj("77949549084")
                    .email("joao.silva@email.test")
                    .name("Joao Silva")
                    .password("somepass")
                    .role(Role.USER)
                    .build());

            var user2 = userService.create(CreateUserDTO.builder()
                    .amount(new BigDecimal("100"))
                    .cpfCnpj("18841355018")
                    .email("maria.silva@email.test")
                    .name("Maria Silva")
                    .password("somepass")
                    .role(Role.USER)
                    .build());

            var shopkeeper = userService.create(CreateUserDTO.builder()
                    .amount(new BigDecimal("100"))
                    .cpfCnpj("82418791000139")
                    .email("company@email.test")
                    .name("Shop Store")
                    .password("somepass")
                    .role(Role.SHOPKEEPER)
                    .build());
            log.info("---Registered User---\nUser1: {}\nUser2: {}\nUser3: {}", user1, user2, shopkeeper);
        };
    }
}
