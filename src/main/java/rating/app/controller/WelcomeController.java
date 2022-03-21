package rating.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {


    @GetMapping("/welcome")
    public ResponseEntity<Map<String, String>> welcomeMessage(@RequestParam("name") String name){
        Map m = new HashMap();
        m.put("message", "app welcomes you "+name);
        return ResponseEntity.ok(m);
    }

    @GetMapping()
    public ResponseEntity<Map<String, String>> defaultMessage(){
        Map m = new HashMap();
        m.put("message", "app welcomes you Naman");
        return ResponseEntity.ok(m);
    }

}
