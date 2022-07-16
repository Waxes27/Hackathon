package za.co.wethinkcode.hackathon.Controllers;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.wethinkcode.hackathon.Service.HackathonService;

@RestController
@RequestMapping("/api/v1/hackathon")
public class HackathonController {

    private final HackathonService hackathonService;

    @Autowired
    public HackathonController(HackathonService service){
        this.hackathonService = service;
    }


    @GetMapping
    public JSONObject getAllHackathons(){
        JSONObject jsonObject = new JSONObject();
        JSONObject hackathonJsonObject = hackathonService.getAllHackathons();
        jsonObject.put("object",hackathonJsonObject);

        return jsonObject;
    }

}
