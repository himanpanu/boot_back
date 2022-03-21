package rating.app.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalField;

@Document("reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reviews {

    @Id
    private String id;
    @Min(1) @Max(5)
    private Byte ratingStar;
    private String businessId;
    private String businessName;
    private String userId;
    private String message;
    @Builder.Default
    private String createdDate = Instant.now().toString();
    private String modifiedDate;
    @Transient
    private String firstName;
    @Transient
    private String lastName;

}
