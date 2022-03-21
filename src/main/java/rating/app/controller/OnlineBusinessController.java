package rating.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rating.app.entity.Categories;
import rating.app.entity.OnlineBusiness;
import rating.app.model.GlobalResponse;
import rating.app.model.SearchModel;
import rating.app.service.OnlineBusinessService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/onlinebusiness")
public class OnlineBusinessController {

    @Autowired
    OnlineBusinessService onlineBusinessServiceService;

    @GetMapping("/categories")
    public ResponseEntity<GlobalResponse> retrieveAllCategories(){
        return ResponseEntity.ok(onlineBusinessServiceService.getAllCategories());
    }

    @PostMapping("/categories")
    public ResponseEntity<GlobalResponse> Categories(@Valid @RequestBody Categories categories) {
        GlobalResponse response = onlineBusinessServiceService.createNewCategories(categories);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<GlobalResponse> registerBusiness(@RequestBody OnlineBusiness business){
        return ResponseEntity.ok(onlineBusinessServiceService.registerBusiness(business));
    }

    @PostMapping("/search")
    public ResponseEntity<GlobalResponse> findAllBusiness(@RequestBody SearchModel searchModel){
        return ResponseEntity.ok(onlineBusinessServiceService.searchBusiness(searchModel));
    }

    @GetMapping("/{onlineBusinessId}")
    public ResponseEntity<GlobalResponse> findBusinessDetails(@PathVariable("onlineBusinessId") String onlineBusinessId){
        GlobalResponse response = onlineBusinessServiceService.getBusinessDetails(onlineBusinessId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<GlobalResponse> getMatchingBusiness(@RequestParam String inputString){
        return ResponseEntity.ok(onlineBusinessServiceService.getMatchingStrings(inputString));
    }

    @GetMapping("/explore")
    public ResponseEntity<GlobalResponse> exploreBusinessForDashboard(){
        return ResponseEntity.ok(onlineBusinessServiceService.getAllExploreBusiness());
    }

}
