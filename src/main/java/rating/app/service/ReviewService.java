package rating.app.service;

import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import rating.app.entity.OnlineBusiness;
import rating.app.entity.Reviews;
import rating.app.entity.User;
import rating.app.model.GlobalResponse;
import rating.app.model.ReviewData;
import rating.app.repository.ReviewsRepository;
import rating.app.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    ReviewsRepository reviewsRepository;

    @Autowired
    OnlineBusinessService businessService;

    @Autowired
    UserRepository userRepository;

    public GlobalResponse getReviewForBusiness(String businessId) {
        List<Reviews> reviews = reviewsRepository.findByBusinessIdOrderByCreatedDateDesc(businessId);
        Map<String, Reviews> users = reviews.stream().collect(Collectors.toMap(Reviews::getUserId, review -> review));

        List<User> userDetails = userRepository.findByEmailIdIn(users.keySet());
        Map<String, User> usersMap = userDetails.stream().collect(Collectors.toMap(User::getEmailId, userDoc->userDoc));

        reviews.stream().forEach(review -> {
            if(Optional.ofNullable(usersMap.get(review.getUserId())).isPresent()){
                review.setFirstName(usersMap.get(review.getUserId()).getFirstName());
                review.setLastName(usersMap.get(review.getUserId()).getLastName());
            }
        });
        return GlobalResponse.builder().data(reviews).isSuccess(true).build();
    }


    public GlobalResponse getReviewsByUser(String userId) {
       return GlobalResponse.builder().data(reviewsRepository.findByUserIdOrderByCreatedDateDesc(userId)).isSuccess(true).build();
    }


    public GlobalResponse createReviewForBusiness(Reviews reviews) {
        GlobalResponse response = null;
        Boolean reviewExists = reviewsRepository.existsByBusinessIdAndUserId(reviews.getBusinessId(), reviews.getUserId());
        if (reviewExists) {
            response = GlobalResponse.builder().message("Review already added!!").isSuccess(false).build();
        } else {
            Reviews review = reviewsRepository.insert(reviews);
            OnlineBusiness business = businessService.updateRatingSumAndIncrementReview(reviews.getBusinessId(), reviews.getRatingStar());

            ReviewData data = new ReviewData(business.getReviewCount(), business.getRatingAverage(),
                    business.getRatingSum(), review);

            response = GlobalResponse.builder().data(data).isSuccess(true).build();
        }
        return response;
    }

}
