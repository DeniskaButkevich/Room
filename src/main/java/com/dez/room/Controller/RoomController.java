package com.dez.room.Controller;

import com.dez.room.Repo.RoomRepo;
import com.dez.room.data.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomController {

    @Autowired
    RoomRepo roomRepo;

    @GetMapping("room/{id}")
    public String getRoom(@PathVariable String id, Model model){

        Room room =roomRepo.findById(Long.parseLong(id)).get();
        model.addAttribute("room", room);

        return "room";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello, " + message + "!";
    }

}
