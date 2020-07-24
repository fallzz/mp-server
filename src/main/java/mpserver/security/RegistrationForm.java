package mpserver.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

import mpserver.domain.Account;

@Data
public class RegistrationForm {
    private String id;
    private String username;
    private String password;
    private String repassword;
    private String phoneNumber;

    public Account toAccount(PasswordEncoder passwordEncoder) {
        return new Account(id, username, passwordEncoder.encode(password), phoneNumber);
    }
}
