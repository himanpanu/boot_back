package rating.app.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Document("reaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reaction {

    @Id
    private String id;
    private String businessId;
    private String userId;
    @Builder.Default
    private String reactionType = "bookmark";
    private String createdDate = Instant.now().toString();
}
