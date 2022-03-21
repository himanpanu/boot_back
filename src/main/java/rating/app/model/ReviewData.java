package rating.app.model;

import lombok.*;
import rating.app.entity.Reviews;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewData extends Reviews {

    private Long ratingSum;
    private long reviewCount;
    private long ratingAverage;

    public void setReviewDetails(Reviews reviews){
        this.setId(reviews.getId());
        this.setRatingStar(reviews.getRatingStar());
        this.setBusinessId(reviews.getBusinessId());
        this.setBusinessName(reviews.getBusinessName());
        this.setUserId(reviews.getUserId());
        this.setMessage(reviews.getMessage());
        this.setCreatedDate(reviews.getCreatedDate());
        this.setModifiedDate(reviews.getModifiedDate());
    }

    public ReviewData(Long ratingSum, long reviewCount, long ratingAverage, Reviews reviews){
        this.ratingSum = ratingSum;
        this.reviewCount = reviewCount;
        this.ratingAverage = ratingAverage;
        setReviewDetails(reviews);
    }

}
