package rating.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rating.app.entity.Location;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchModel {

    private String searchString;
    private Location searchLocation;

}
