package rating.app.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Document("onlineBusiness")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlineBusiness {


    @Id
    private String id;
    private List<String> categories;
    private String description;
    private Integer followerCount;
    private String instagram;
    private String address;
    private String name;
    private String contact;
    private List<String> serviceLocation;
    private String website;
    private String createdDate = Instant.now().toString();
    private String createdBy;
    private String modifiedDate;
    private String modifiedBy;
    private Long ratingSum;
    private long reviewCount;
    private String iconUrl;
    private List<String> headerUrl;
    private  String thumbnailUrl;
    private long ratingAverage;
    @Builder.Default
    private boolean explore = false;
    @Transient
    private String instagramUrl;




    public void aggregateRatingSumAndIncrementReview(Byte rating) {
        reviewCount = ObjectUtils.isEmpty(reviewCount) ? 1 : ++reviewCount;
        ratingSum = ObjectUtils.isEmpty(ratingSum) ? rating : ratingSum + rating;
        ratingAverage = ratingSum / reviewCount;
        modifiedBy = Instant.now().toString();
    }

    public String getInstagramUrl(){
        if(StringUtils.hasText(instagram)){
            return "https://instagram.com/"+instagram;
        }
        return null;
    }

}
