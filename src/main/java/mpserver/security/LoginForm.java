package mpserver.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginForm {
    @NotBlank
    @Size(min=1, max=20)
    private String id;

    @NotBlank
    private String password;
}
