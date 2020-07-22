package mpserver.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

import mpserver.domain.Appointment;

@Repository
public class JdbcAppointmentRepository implements AppointmentRepository {

    private SimpleJdbcInsert appointmentInserter;
    private SimpleJdbcInsert accountAppointmentInserter;
    private ObjectMapper objectMapper;

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcAppointmentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.appointmentInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Appointment")
                .usingGeneratedKeyColumns("id");
        this.accountAppointmentInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Account_Appointment");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Appointment save(Appointment appointment) {
        appointment.setCreatedAt(new Date());
        long appointmentId = saveAppointmentDetails(appointment);
        appointment.setId(appointmentId);

        List<String> members = appointment.getMembers();
        for(String member : members) {
            saveAccountToAppointment(member, appointmentId);
        }

        return appointment;
    }

    private long saveAppointmentDetails(Appointment appointment) {
        Map<String, Object> values = objectMapper.convertValue(appointment, Map.class);
        values.put("created_at", appointment.getCreatedAt());

        long appointmentId = appointmentInserter
                .executeAndReturnKey(values)
                .longValue();

        return appointmentId;
    }

    private void saveAccountToAppointment(String member, long appointmentId) {
        Map<String, Object> values = new HashMap<>();
        values.put("account", member);
        values.put("appointment", appointmentId);
        accountAppointmentInserter.execute(values);
    }
}
