package za.co.wethinkcode.hackathon.Service;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.wethinkcode.hackathon.Models.Hackathon;
import za.co.wethinkcode.hackathon.Repositories.HackathonRepository;

import java.util.List;

@Service
public class HackathonService {
    private final HackathonRepository hackathonRepository;

    @Autowired
    public HackathonService(HackathonRepository hackathonRepository){
        this.hackathonRepository = hackathonRepository;
    }

    public JSONObject getAllHackathons(){
        JSONObject jsonObject = new JSONObject();


        List<Hackathon> hackathonList = hackathonRepository.findAll();
        System.out.println(hackathonList);

        jsonObject.put("response",hackathonList);
        System.out.println(jsonObject);

        return jsonObject;
    }

    public JSONObject addHackathon(Hackathon hackathon){
        hackathonRepository.save(hackathon);
        JSONObject jsonObject = new JSONObject();

        jsonObject.append("response",true);
        return jsonObject;
    }
}
