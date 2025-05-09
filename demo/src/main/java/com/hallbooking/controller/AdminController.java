package com.hallbooking.controller;


import com.hallbooking.model.Booking;
import com.hallbooking.model.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import java.util.List;



    @Controller
    @RequestMapping("/admin")
    public class AdminController {
    
        @Autowired
        private BookingRepository bookingRepository;
    
      
        @GetMapping("/bookings")
        public String viewAllSubmittedBookings(Model model) {
           List<Booking> bookings = bookingRepository.findByStatus("Submitted"); // Only pending bookings for admin view table
           model.addAttribute("bookings", bookings);
           return "admin-booking"; 
        }
    
        @PostMapping("/bookings/{id}/approve")
        public String approveBooking(@PathVariable Long id) {
            Booking booking = bookingRepository.findById(id).orElseThrow();
            booking.setStatus("Approved");
            bookingRepository.save(booking);
            return "admin-booking";
        }
    
        @PostMapping("/bookings/{id}/reject")
        public String rejectBooking(@PathVariable Long id) {
            Booking booking = bookingRepository.findById(id).orElseThrow();
            booking.setStatus("Rejected");
            bookingRepository.save(booking);
            return "admin-booking";
        }
    }
    