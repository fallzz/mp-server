package mpserver.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import mpserver.data.AccountRepository;
import mpserver.security.RegistrationForm;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path="/register", produces="application/json")
@CrossOrigin(origins="*")
public class RegistrationController {
    private AccountRepository accountRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(AccountRepository accountRepo, PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String processRegistration(@RequestBody @Valid RegistrationForm form, Errors errors) {
        if(errors.hasErrors()) {
            log.info("Error: " + errors);
            return "registration failed";
        }
        accountRepo.save(form.toAccount(passwordEncoder));
        return "registration completed";
    }
}
