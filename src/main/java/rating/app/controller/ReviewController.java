package rating.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rating.app.entity.Reviews;
import rating.app.entity.User;
import rating.app.model.GlobalResponse;
import rating.app.service.ReviewService;
import rating.app.service.UserService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/business/{businessId}")
    public ResponseEntity<GlobalResponse> getReviewsForBusiness(@PathVariable("businessId") String businessId){
        return ResponseEntity.ok(reviewService.getReviewForBusiness(businessId));
    }

    @PostMapping("/{userId}/{businessId}")
    public ResponseEntity<GlobalResponse> writeReview(@RequestBody Reviews reviews, @PathVariable("userId") String userId,
                                            @PathVariable("businessId") String businessId){
        reviews.setUserId(userId);
        reviews.setBusinessId(businessId);
        return ResponseEntity.ok(reviewService.createReviewForBusiness(reviews));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GlobalResponse> getMyReviews(@PathVariable("userId") String userId){
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

}
