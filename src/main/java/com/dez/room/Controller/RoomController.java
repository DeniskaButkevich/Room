package com.dez.room.Controller;

import com.dez.room.Repo.CountryRepo;
import com.dez.room.Repo.RoomRepo;
import com.dez.room.data.Room;
import com.dez.room.util.IpUtil;
import com.github.sypexgeo.SxRestClient;
import com.github.sypexgeo.model.SxGeoResult;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class RoomController {

    private RoomRepo roomRepo;
    private CountryRepo countryRepo;

    public RoomController(RoomRepo roomRepo, CountryRepo countryRepo){
        this.roomRepo = roomRepo;
        this.countryRepo = countryRepo;
}

    @GetMapping("room/{id}")
    public String getRoom(@PathVariable String id, Model model, HttpServletRequest request){

        SxGeoResult result = SxRestClient.create("").get(IpUtil.fetchClientIpAddr(request));
        roomRepo.findById(Long.parseLong(id)).get();
        if(result.country != null){
            Optional<Room> optional_room = roomRepo.findById(Long.parseLong(id));
            if(optional_room != null){
                Room  room =  optional_room.get();
                if(result.country.name.en().equals(room.getCountry().getNameEn())){
                    model.addAttribute("room", room);
                    return "room";
                }
            }
        }
        model.addAttribute("room", null);
        return "room";
    }

    @MessageMapping("/room")
    @SendTo("/topic/lamp")
    public String turning(String id) {
        Room room = roomRepo.findById(Long.parseLong(id)).get();

        boolean flag =  room.isLamp() == true ? false : true;
        room.setLamp(flag);

        roomRepo.save(room);
        return  id + "-" + room.isLamp();
    }

}
