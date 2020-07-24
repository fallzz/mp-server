package mpserver.security;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import mpserver.data.AccountRepository;
import mpserver.domain.Account;

@Service(value="CustomAccountDetailsService")
public class CustomAccountDetailsService implements UserDetailsService {
    private AccountRepository accountRepo;

    @Autowired
    public CustomAccountDetailsService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Account account = accountRepo.findById(id);
        if(account != null) {
            return account;
        }
        throw new UsernameNotFoundException("User '" + id + "' not found");
    }
}
