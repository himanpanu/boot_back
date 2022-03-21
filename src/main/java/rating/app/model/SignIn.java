package rating.app.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignIn {
    @NotBlank
    private String password;
}
