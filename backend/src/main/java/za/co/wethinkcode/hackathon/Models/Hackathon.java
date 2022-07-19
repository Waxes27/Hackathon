package za.co.wethinkcode.hackathon.Models;


import lombok.Getter;
import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
public class Hackathon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String body;
    private LocalDate date;

    public Hackathon() {
    }


    public Hackathon(String title, String body){
        this.title = title;
        this.body = body;
        this.date = LocalDate.now();

    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("title",title);
        jsonObject.put("body",body);
        jsonObject.put("date",date);
        return jsonObject.toString();
    }
}
