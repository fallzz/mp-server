package mpserver.data;

import mpserver.domain.Account;

public interface AccountRepository {
    Account save(Account account);
    Account findById(String id);
}