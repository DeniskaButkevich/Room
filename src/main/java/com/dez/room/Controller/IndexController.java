package com.dez.room.Controller;

import com.dez.room.Repo.RoomRepo;
import com.dez.room.data.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;


@Controller
public class IndexController {

    private RoomRepo roomRepo;

    public IndexController(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @GetMapping("index")
    public String getIndexPage(Model model){

        ArrayList<String> arrayCountries = new ArrayList<>();
        arrayCountries.add("Belarus");
        arrayCountries.add("Russian");
        arrayCountries.add("Germany");

        model.addAttribute("countries", arrayCountries);
        model.addAttribute("rooms", roomRepo.findAll());

        return "index";
    }

    @PostMapping("index")
    public String addCountry(Room room){

        room.setLamp(false);
        roomRepo.save(room);
        return "redirect:/index";
    }


}
