package com.basumatarau.imProject.consumer;

import com.basumatarau.imProject.persistence.lib.model.user.AuthenticationDetails;
import com.basumatarau.imProject.persistence.lib.model.user.User;
import com.basumatarau.imProject.persistence.lib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerTrials implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerTrials.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AuthenticationDetails localDetails
                = AuthenticationDetails.builder().authProvider("local").build();

        userRepository.saveAndFlush(
                User.userBuilder()
                        .isEnabled(true)
                        .details(localDetails)
                        .role(User.UserRole.USER)
                        .isEnabled(true)
                        .firstName("TestFirstName1")
                        .lastName("TestLastName1")
                        .nickName("TestNickName1")
                        .email("test@email.com1")
                        .passwordHash("testStub1")
                        .build()
        );

        String email = userRepository.findByEmail("test@email.com1").orElseThrow(() -> new RuntimeException("fail")).getEmail();
        System.out.println("success!!!! email: " + email);
    }
}
