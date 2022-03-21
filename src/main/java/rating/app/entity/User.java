package rating.app.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Document("user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @NotBlank(message = "Email id must not be blank")
    private String emailId;
    private String gender;
    private String firstName;
    private String lastName;
    private String dob;
    private String phone;
    private String iconUrl;
    private String createdDate = Instant.now().toString();
    private String modifiedDate ;
    private List<String> favouriteBusiness;
    private String password;

}
