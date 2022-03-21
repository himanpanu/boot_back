package rating.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rating.app.entity.OnlineBusiness;
import rating.app.entity.Reaction;
import rating.app.model.GlobalResponse;
import rating.app.repository.OnlineBusinessRepository;
import rating.app.repository.ReactionRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private OnlineBusinessRepository businessRepository;

    public GlobalResponse toggleBookmarkReaction(String businessId, String userId){
        Map<String, Boolean> finalBookmarkStatus;
        Boolean exists = reactionRepository.existsByBusinessIdAndUserId(businessId, userId);
        if(exists){
            reactionRepository.deleteByBusinessIdAndUserId(businessId, userId);
            finalBookmarkStatus = Collections.singletonMap("bookmarkStatus", false);
        } else {
            Reaction reaction = Reaction.builder().userId(userId).businessId(businessId).build();
            reactionRepository.insert(reaction);
            finalBookmarkStatus = Collections.singletonMap("bookmarkStatus", true);
        }
        return GlobalResponse.builder().isSuccess(true).data(finalBookmarkStatus).build();
    }

    public GlobalResponse getBookmarkedBusinessesByUserId(String userId){
        List<Reaction> reactions = reactionRepository.findByUserIdAndReactionType(userId, "bookmark");
        List<String> businessIds = reactions.stream().map(Reaction::getBusinessId).collect(Collectors.toList());
        List<OnlineBusiness> businesses = businessRepository.findAllWithIdInSelectedProjection(businessIds);
        Map<String, OnlineBusiness> buss = businesses.stream().collect(Collectors.toMap(OnlineBusiness::getId, bus->bus ));
        List<OnlineBusiness> sortedBusiness = businessIds.stream()
                .filter(businessId -> buss.get(businessId)!=null)
                .map(businessId -> buss.get(businessId)).collect(Collectors.toList());

        return GlobalResponse.builder().data(sortedBusiness).isSuccess(true).build();

    }




}
