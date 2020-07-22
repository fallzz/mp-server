package mpserver.data;

import mpserver.domain.Appointment;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
}
