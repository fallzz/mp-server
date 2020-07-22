package mpserver.data;

import mpserver.domain.Account;

public interface AccountRepository {
    Account findById(String id);
    Account save(Account account);
}
