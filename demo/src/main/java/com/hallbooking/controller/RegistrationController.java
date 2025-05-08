package com.hallbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.hallbooking.model.MyAppUser;
import com.hallbooking.model.MyAppUserRepository;
import com.hallbooking.service.EmailService;
import com.hallbooking.utils.JwtTokenUtil;

@RestController
public class RegistrationController {
    
    @Autowired
    private MyAppUserRepository myAppUserRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    
    
    @PostMapping(value = "/req/signup", consumes = "application/json")
    public ResponseEntity<String> createUser(@RequestBody MyAppUser user){
        
        MyAppUser existingAppUser = myAppUserRepository.findByEmail(user.getEmail());
        
        if(existingAppUser != null){
            if(existingAppUser.isVerified()){
                return new ResponseEntity<>("User Already exist and Verified.",HttpStatus.BAD_REQUEST);
            }else{
                String verificationToken = JwtTokenUtil.generateToken(existingAppUser.getEmail());
                existingAppUser.setVerficationToken(verificationToken);
                myAppUserRepository.save(existingAppUser);
              
                emailService.sendVerificationEmail(existingAppUser.getEmail(), verificationToken);
                return new ResponseEntity<>("Verification Email resent. Check your inbox",HttpStatus.OK);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String vericationToken =JwtTokenUtil.generateToken(user.getEmail());
        user.setVerficationToken(vericationToken);
        myAppUserRepository.save(user);
       
        emailService.sendVerificationEmail(user.getEmail(), vericationToken);
        
        return new ResponseEntity<>("Registration successfull! Please Verify your Email", HttpStatus.OK);
    }
    
}