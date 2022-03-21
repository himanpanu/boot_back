package rating.app.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Document("categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Categories {

    @Id
    private String id;
    @NotBlank
    private String name;
}
