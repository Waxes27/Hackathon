package za.co.wethinkcode.hackathon.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.wethinkcode.hackathon.Models.User;
import za.co.wethinkcode.hackathon.Repositories.UserRepository;

@Service
public class RegistrationService {
    UserRepository userRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public JSONObject registerUser(User user){
        JSONObject jsonObject = new JSONObject();

        userRepository.save(user);


        jsonObject.put("object","user added");

        return jsonObject;
    }
}
