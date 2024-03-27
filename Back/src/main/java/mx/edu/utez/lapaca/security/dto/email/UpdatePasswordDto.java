package mx.edu.utez.lapaca.security.dto.email;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePasswordDto {

    @NotEmpty
    private String email;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String secretPass;

}