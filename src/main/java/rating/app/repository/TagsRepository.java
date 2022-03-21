package rating.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rating.app.entity.Tags;

@Repository
public interface TagsRepository extends MongoRepository<Tags, String> {
}
