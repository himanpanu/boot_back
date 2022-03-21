package rating.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rating.app.entity.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmailIdAndPassword(String id, String password);

    @Query(value = "{emailId: {$in: ?0}}",
            fields = "{firstName:1, lastName:1}")
    List<User> findByEmailIdIn(Set<String> ids);

}
