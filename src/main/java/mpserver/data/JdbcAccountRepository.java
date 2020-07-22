package mpserver.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Map;

import java.sql.ResultSet;
import java.sql.SQLException;

import mpserver.domain.Account;

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
    }

    @Override
    public Account findById(String id) {
        return jdbc.queryForObject(
                "select id, phone_number, name, password where id=?",
                this::mapRowToAccount,
                id
        );
    }

    private Account mapRowToAccount(ResultSet rs, int rowNum) throws SQLException {
        return new Account(
                rs.getString("id"),
                rs.getString("phone_number"),
                rs.getString("person_name"),
                rs.getString("password")
        );
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
}