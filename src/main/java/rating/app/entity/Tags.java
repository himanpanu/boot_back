package rating.app.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("tags")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tags {

    @Id
    private String id;
    private String value;
    @Builder.Default
    private String createdDate = Instant.now().toString();
    private String ModifiedDate;

}
