package rating.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rating.app.entity.Reaction;
import rating.app.entity.User;

import java.util.List;
import java.util.Set;

@Repository
public interface ReactionRepository extends MongoRepository<Reaction, String> {

    boolean existsByBusinessIdAndUserId(String businessId, String userId);

    void deleteByBusinessIdAndUserId(String businessId, String userId);

    List<Reaction> findByUserIdAndReactionType(String userId, String reactionType);
}
