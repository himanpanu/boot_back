package rating.app.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalResponse {

    Boolean isSuccess;
    String message;
    Object data;


}
