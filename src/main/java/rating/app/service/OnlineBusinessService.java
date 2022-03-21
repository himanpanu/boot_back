package rating.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import rating.app.entity.Categories;
import rating.app.entity.OnlineBusiness;
import rating.app.model.GlobalResponse;
import rating.app.model.SearchModel;
import rating.app.repository.CategoryRepository;
import rating.app.repository.OnlineBusinessRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class OnlineBusinessService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OnlineBusinessRepository onlineBusinessRepository;

    public GlobalResponse getAllCategories(){
        return GlobalResponse.builder().isSuccess(true).data(categoryRepository.findAll()).build();
    }

    public GlobalResponse createNewCategories(Categories categories){
        GlobalResponse response = null;
        if(!categoryRepository.existsByName(categories.getName().toLowerCase())){
            Categories persistedCategory = categoryRepository.insert(categories);
            response = GlobalResponse.builder().isSuccess(true).data(persistedCategory).build();
        } else{
            log.error("already available in system");
            response = GlobalResponse.builder().isSuccess(false).message("already available in system").build();
        }
        return response;
    }

    public GlobalResponse registerBusiness(OnlineBusiness business){
       return GlobalResponse.builder()
                .data(onlineBusinessRepository.save(business)).isSuccess(true).build();
    }

    public GlobalResponse searchBusiness(SearchModel searchModel){
        List<OnlineBusiness> searchResults;
        if( !StringUtils.hasText(searchModel.getSearchLocation().getState()) && !StringUtils.hasText(searchModel.getSearchLocation().getCity()) &&
                StringUtils.hasText(searchModel.getSearchString())){
            searchResults= onlineBusinessRepository.findAllWithSelectedProjection(searchModel.getSearchString());
        } else if (StringUtils.hasText(searchModel.getSearchString())) {
            searchResults= onlineBusinessRepository.findAllWithSelectedProjection(searchModel.getSearchString(), searchModel.getSearchLocation().getValidLocations());
        } else if(!StringUtils.hasText(searchModel.getSearchString())) {
            searchResults = onlineBusinessRepository.findAllWithLocationSelectedProjection(searchModel.getSearchLocation().getValidLocations());
        } else {
            searchResults = onlineBusinessRepository.findAllWithLocationSelectedProjection();
        }
        return GlobalResponse.builder()
                        .isSuccess(true)
                        .data(searchResults).build();
    }

    public GlobalResponse getBusinessDetails(String businessId) {
        GlobalResponse response = null;
        Optional<OnlineBusiness> business = onlineBusinessRepository.findById(businessId);
        if (!business.isPresent()) {
            response = GlobalResponse.builder().message("Business not found").isSuccess(false).build();
        } else {
            response = GlobalResponse.builder().data(business.get()).isSuccess(true).build();
        }
        return response;
    }

    public OnlineBusiness updateRatingSumAndIncrementReview(String businessId, Byte rating){
        OnlineBusiness onlineBusiness = null;
        Optional<OnlineBusiness> business = onlineBusinessRepository.findById(businessId);
        if(business.isPresent()){
            business.get().aggregateRatingSumAndIncrementReview(rating);
            onlineBusiness = onlineBusinessRepository.save(business.get());
        } else {
            log.error("Business not found with businessId {}", businessId);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "No business found");
        }
        return onlineBusiness;
    }

    public GlobalResponse getMatchingStrings(String inputString){
        inputString = "^".concat(inputString);
        Stream<Categories> categories = categoryRepository.findByName(inputString);
        Stream<OnlineBusiness> onlineBusinesses = onlineBusinessRepository.findAllWithSelectedProjectionForAutocomplete(inputString);
        List<String> categoriesString = categories.limit(5).map(Categories::getName).collect(Collectors.toList());
        List<String> business = onlineBusinesses.limit(5).map(OnlineBusiness::getName).collect(Collectors.toList());
        business.addAll(categoriesString);
        return GlobalResponse.builder().data(business).isSuccess(true).build();
    }

    public GlobalResponse getAllExploreBusiness(){
        List<OnlineBusiness> exploreBusinesses = onlineBusinessRepository.findByExplore(true);
        return GlobalResponse.builder().data(exploreBusinesses).isSuccess(true).build();
    }


}
