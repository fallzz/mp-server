package mpserver.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import mpserver.domain.Account;
import mpserver.security.LoginForm;
import mpserver.security.RegistrationForm;
import mpserver.security.JwtTokenProvider;
import mpserver.data.AccountRepository;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins="*")
public class AccountController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepo;

    @PostMapping(path="/register", consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String join(@RequestBody @Valid RegistrationForm form, Errors errors) {
        if(errors.hasErrors()) {
            return "registration failed";
        }
        accountRepo.save(form.toAccount(passwordEncoder));
        return "registration completed";
    }

    @PostMapping(path="/login", consumes="application/json")
    public String login(@RequestBody @Valid LoginForm form, Errors errors) {
        if(errors.hasErrors()) {
            return "login failed";
        }
        Account member = accountRepo.findById(form.getId());
        if(!passwordEncoder.matches(form.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("wrong password");
        }
        return jwtTokenProvider.createToken(member.getId(), member.getRoles());
    }
}
