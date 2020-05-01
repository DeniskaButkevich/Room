package com.dez.room.Controller;

import com.dez.room.Repo.CountryRepo;
import com.dez.room.Repo.RoomRepo;
import com.dez.room.data.Room;
import com.dez.room.util.IpUtil;
import com.github.sypexgeo.SxRestClient;
import com.github.sypexgeo.model.SxGeoResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    private RoomRepo roomRepo;
    private CountryRepo countryRepo;

    public IndexController(RoomRepo roomRepo, CountryRepo countryRepo) {
        this.roomRepo = roomRepo;
        this.countryRepo = countryRepo;
    }

    @GetMapping()
    public String getIndexPage(Model model, HttpServletRequest request){
        SxGeoResult result = SxRestClient.create("").get(IpUtil.fetchClientIpAddr(request));

        String country = result.country == null ? "Anonymous" : result.country.name.en();
        model.addAttribute("myCountry", country);
        model.addAttribute("rooms", roomRepo.findAll());
        model.addAttribute("countries", countryRepo.findAll());

        return "index";
    }

    @PostMapping()
    public String addCountry(@RequestParam String name, @RequestParam Short id, Model model, HttpServletRequest request){
        if(id != null){
            Room room = new Room();
            room.setCountry(countryRepo.findById(id).get());
            room.setName(name);
            room.setLamp(false);
            roomRepo.save(room);
        }
        return getIndexPage(model, request);
    }

    @PostMapping("/room/delete/{id}")
    public String deleteRoom(@PathVariable String id){
        Room room = roomRepo.findById(Long.parseLong(id)).get();
        roomRepo.delete(room);
        return "redirect:/";
    }


}
