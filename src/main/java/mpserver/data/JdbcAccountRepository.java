package mpserver.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import mpserver.domain.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Repository
public class JdbcAccountRepository implements AccountRepository {
    private SimpleJdbcInsert accountInserter;
    private ObjectMapper objectMapper;

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcAccountRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.accountInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Account");
        this.objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Override
    public Account save(Account account) {
        saveAccountDetails(account);
        return account;
    }

    private void saveAccountDetails(Account account) {
        Map<String, Object> values = objectMapper.convertValue(account, Map.class);
        accountInserter.execute(values);
    }

    @Override
    public Account findById(String id) {
        return jdbc.queryForObject(
                "select id, username, password, phone_number from Account where id=?",
                this::mapRowToAccount,
                id
        );
    }

    private Account mapRowToAccount(ResultSet rs, int rowNum) throws SQLException {
        return new Account(
                rs.getString("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("phone_number")
        );
    }
}
