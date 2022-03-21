package rating.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rating.app.entity.Categories;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CategoryRepository extends MongoRepository<Categories, String> {

    Boolean existsByName(String name);

    @Query(value = "{name: {$regex: ?0}}",
            fields = "{name:1}")
    Stream<Categories> findByName(String name);

}
