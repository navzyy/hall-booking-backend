package com.hallbooking.controller;

import com.hallbooking.model.Hall;
import com.hallbooking.model.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@Controller
public class ContentController {
    
     @Autowired
    private HallRepository hallRepository;

    @GetMapping("/req/login")
    public String login(){
        return "login";
    }
    
    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

      @GetMapping("/index")
    public String showHalls(Model model) {
        List<Hall> halls = hallRepository.findAll();
        model.addAttribute("halls", halls);
        return "index"; 
    }
   
 

}
