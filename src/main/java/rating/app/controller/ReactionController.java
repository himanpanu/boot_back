package rating.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rating.app.model.GlobalResponse;
import rating.app.service.ReactionService;

@RestController
@RequestMapping("/reaction")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping("/{userId}/{businessId}/tooglebookmark")
    public ResponseEntity<GlobalResponse> toggleBookmark(@PathVariable("businessId") String businessId,
                                         @PathVariable("userId") String userId){
        GlobalResponse reactionUpdateBody = reactionService.toggleBookmarkReaction(businessId, userId);
        return ResponseEntity.ok(reactionUpdateBody);
    }

    @GetMapping("/{userId}/businesses")
    public ResponseEntity<GlobalResponse> getBookmarkedBusinessesByUser(@PathVariable("userId") String userId){
        GlobalResponse reactionUpdateBody = reactionService.getBookmarkedBusinessesByUserId(userId);
        return ResponseEntity.ok(reactionUpdateBody);
    }

}
