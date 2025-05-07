package com.hallbooking.controller;


import com.hallbooking.model.Booking;
import com.hallbooking.model.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;



    @Controller
    @RequestMapping("/admin")
    public class AdminController {
    
        @Autowired
        private BookingRepository bookingRepository;
    
        @GetMapping("/bookings")
        public String viewAllBookings(Model model) {
            List<Booking> bookings = bookingRepository.findAll();
            model.addAttribute("bookings", bookings);
            return "admin-booking";
        }
    
        @PostMapping("/bookings/{id}/approve")
        public String approveBooking(@PathVariable Long id) {
            Booking booking = bookingRepository.findById(id).orElseThrow();
            booking.setStatus("Approved");
            bookingRepository.save(booking);
            return "redirect:/admin/booking";
        }
    
        @PostMapping("/bookings/{id}/reject")
        public String rejectBooking(@PathVariable Long id) {
            Booking booking = bookingRepository.findById(id).orElseThrow();
            booking.setStatus("Rejected");
            bookingRepository.save(booking);
            return "redirect:/admin/bookings";
        }
    }
    