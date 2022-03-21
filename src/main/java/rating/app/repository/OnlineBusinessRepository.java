package rating.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rating.app.entity.OnlineBusiness;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface OnlineBusinessRepository extends MongoRepository<OnlineBusiness, String> {

    @Query(value = "{ $or: [{categories: {$regex: /?0/, $options: 'i'}}, {name: {$regex: /?0/, $options: 'i'}}], serviceLocation: {$in:?1}}",
            fields = "{ratingAverage:1, reviewCount:1, name:1, followerCount:1, iconUrl:1, thumbnailUrl:1, createdDate:1, categories:1, headerUrl:1}", sort = "{ratingAverage:1}")
    List<OnlineBusiness> findAllWithSelectedProjection(String searchString, Set<String> serviceLocation);

    @Query(value = "{ $or: [{categories: {$regex: /?0/, $options: 'i'}}, {name: {$regex: /?0/, $options: 'i'}}]}",
            fields = "{ratingAverage:1, reviewCount:1, name:1, followerCount:1, iconUrl:1, thumbnailUrl:1, createdDate:1, categories:1, headerUrl:1}", sort = "{ratingAverage:1}")
    List<OnlineBusiness> findAllWithSelectedProjection(String searchString);

    @Query(value = "{serviceLocation: {$in:?0}}",
            fields = "{ratingAverage:1, reviewCount:1, name:1, followerCount:1, iconUrl:1, thumbnailUrl:1, createdDate:1, headerUrl:1}", sort = "{ratingAverage:1}")
    List<OnlineBusiness> findAllWithLocationSelectedProjection(Set<String> serviceLocation);

    @Query(value = "{}",fields = "{ratingAverage:1, reviewCount:1, name:1, followerCount:1, iconUrl:1, thumbnailUrl:1, createdDate:1, headerUrl:1}", sort = "{ratingAverage:1}")
    List<OnlineBusiness> findAllWithLocationSelectedProjection();

    @Query(value = "{id: {$in:?0}}",
            fields = "{ratingAverage:1, reviewCount:1, name:1, followerCount:1, iconUrl:1, thumbnailUrl:1, createdDate:1, headerUrl:1}")
    List<OnlineBusiness> findAllWithIdInSelectedProjection(List<String> businessIds);


    @Query(value = "{name: {$regex: ?0, $options:'i'}}",
            fields = "{name:1, createdDate:1}", sort = "{ratingAverage:1}")
    Stream<OnlineBusiness> findAllWithSelectedProjectionForAutocomplete(String searchString);

    @Query(value = "{name: {$regex: ?0, $options:'i'}}",
            fields = "{name:1, createdDate:1}", sort = "{ratingAverage:1}")
    List<OnlineBusiness> findAllWithSelectedProjectionForAutocompletes(String searchString);

    List<OnlineBusiness> findByExplore(boolean explore);

}
