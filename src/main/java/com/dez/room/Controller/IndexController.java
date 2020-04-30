package com.dez.room.Controller;

import com.dez.room.Repo.CountryRepo;
import com.dez.room.Repo.RoomRepo;
import com.dez.room.data.Country;
import com.dez.room.data.Room;
import com.github.sypexgeo.SxRestClient;
import com.github.sypexgeo.model.SxGeoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class IndexController {

    private RoomRepo roomRepo;

    @Autowired
    private CountryRepo countryRepo;

    public IndexController(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @ModelAttribute(name = "countries")
    public Iterable<Country> getCountries(){return countryRepo.findAll();}

    @GetMapping()
    public String getIndexPage(Model model, HttpServletRequest request){

        SxGeoResult result = SxRestClient.create("").get(fetchClientIpAddr());
        model.addAttribute("rooms", roomRepo.findAll());
        model.addAttribute("myCountry", result.country.name.en());

        return "index";
    }

    @PostMapping("/room/delete/{id}")
    public String deleteRoom(@PathVariable String id){

        Room room = roomRepo.findById(Long.parseLong(id)).get();
        roomRepo.delete(room);
        return "redirect:/";
    }

    @PostMapping()
    public String addCountry(@RequestParam String name,@RequestParam Short id){

        Room room = new Room();

        room.setCountry(countryRepo.findById(id).get());
        room.setName(name);
        room.setLamp(false);
        roomRepo.save(room);
        return "redirect:/";
    }


    @SuppressWarnings("ConstantConditions")
    protected String fetchClientIpAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        if (ip.equals("0:0:0:0:0:0:0:1")) ip = "37.215.2.200";
        Assert.isTrue(ip.chars().filter($ -> $ == '.').count() == 3, "Illegal IP: " + ip);
        return ip;
    }

}
