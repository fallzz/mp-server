package mpserver.domain;

import java.util.List;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Appointment {
    private Long id;
    private Date createdAt;
    private String subject;
    private String content;
    private float longitude;
    private float latitude;
    private String locationName;
    private List<String> members;
    private Date startAt;
    private Date endAt;
}
