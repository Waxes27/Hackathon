package za.co.wethinkcode.hackathon.Controllers;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.wethinkcode.hackathon.Models.Hackathon;
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
    public String getAllHackathons(){
        JSONObject jsonObject = new JSONObject();
        JSONObject hackathonJsonObject = hackathonService.getAllHackathons();
        jsonObject.put("object",hackathonJsonObject);

        return jsonObject.toString();
    }

    @PostMapping
    public String addNewHackathon(@RequestParam("title") String title,@RequestParam("body") String body){
        Hackathon hackathon = new Hackathon(title,body);
        hackathonService.addHackathon(hackathon);
        return "";
    }

}
