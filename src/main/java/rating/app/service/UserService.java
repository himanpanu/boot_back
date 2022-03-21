package rating.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import rating.app.entity.User;
import rating.app.model.GlobalResponse;
import rating.app.repository.UserRepository;
import rating.app.utility.HelperValidation;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public GlobalResponse getUserDetails(String userId){
        GlobalResponse response = null;
        Optional<User> persistedUser = userRepository.findById(userId);
        if(!persistedUser.isPresent()){
            response = GlobalResponse.builder().message("Invalid user!!").isSuccess(false).build();
        } else {
            response = GlobalResponse.builder().data(persistedUser.get()).isSuccess(true).build();
        }
        return response;
    }

    public GlobalResponse createUserDetails(User user) {
        GlobalResponse response = null;
        try {
            HelperValidation.validateEmail(user.getEmailId());
            User persistedResponse = userRepository.insert(user);
            response = GlobalResponse.builder().isSuccess(true).data(persistedResponse).build();
        } catch (DuplicateKeyException duplicateKeyException){
            log.error("email id already exists");
            response = GlobalResponse.builder().isSuccess(false).message("email id already exists").build();
        } catch (RuntimeException validation){
            log.error(validation.getMessage());
            response = GlobalResponse.builder().isSuccess(false).message(validation.getMessage()).build();
        }
        return response;
    }

    public GlobalResponse deleteUser(String userId) {
        GlobalResponse response;
        if(userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            response = GlobalResponse.builder().isSuccess(true).build();
        } else {
            response = GlobalResponse.builder().isSuccess(false).message("User not found").build();
        }
        return response;
    }

    public GlobalResponse updateUserDetails(User user){
        GlobalResponse response = null;
        user.setModifiedDate(Instant.now().toString());
         if(!userRepository.existsById(user.getEmailId())){
             response = GlobalResponse.builder().isSuccess(false).message("Email not allowed to update !!").build();
         } else {
             User updatedUser = userRepository.save(user);
             response = GlobalResponse.builder().data(updatedUser).isSuccess(true).build();
         }
         return response;
    }

    public GlobalResponse authenticateUser(String password, String userId){
        GlobalResponse response = null;
        User userDetails = userRepository.findByEmailIdAndPassword(userId, password);
        if(userDetails == null) {
            response = GlobalResponse.builder().isSuccess(false).message("Email / Password mismatch").build();
        } else {
            response =  GlobalResponse.builder().data(userDetails).isSuccess(true).build();
        }
        return response;
    }

}
