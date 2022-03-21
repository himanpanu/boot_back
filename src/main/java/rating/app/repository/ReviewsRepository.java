package rating.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rating.app.entity.Reviews;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository extends MongoRepository<Reviews, String> {

    List<Reviews> findByBusinessIdOrderByCreatedDateDesc(String businessId);
    List<Reviews> findByUserIdOrderByCreatedDateDesc(String businessId);

    Boolean existsByBusinessIdAndUserId(String businessId, String userId);

}

