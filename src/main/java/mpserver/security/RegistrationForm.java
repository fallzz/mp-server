package mpserver.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

import javax.validation.constraints.*;

import mpserver.domain.Account;

@Data
public class RegistrationForm {

    @NotBlank
    @Size(min=1, max=20)
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    
    @NotBlank
    private String repassword;

    @Pattern(regexp="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    @JsonProperty("phone_number")
    private String phoneNumber;

    public Account toAccount(PasswordEncoder passwordEncoder) {
        return new Account(id, username, passwordEncoder.encode(password), phoneNumber);
    }
}
