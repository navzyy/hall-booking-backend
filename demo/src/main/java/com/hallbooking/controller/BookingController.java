package com.hallbooking.controller;

import com.hallbooking.model.Booking;
import com.hallbooking.model.Hall;
import com.hallbooking.model.BookingRepository;
import com.hallbooking.model.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HallRepository hallRepository;

    @GetMapping("/book/{hallId}")
public String showBookingForm(@PathVariable Long hallId, Model model) {
    Hall hall = hallRepository.findById(hallId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid hall ID"));

    Booking booking = new Booking();
    booking.setHall(hall); // ✅ Important to link hall

    model.addAttribute("booking", booking);
    model.addAttribute("hall", hall); // ✅ Required for ${hall.name}
    return "booking-form";
}

    @PostMapping("/book")
public String submitBooking(@ModelAttribute Booking booking, Model model) {
    // ✅ Fetch full Hall entity from DB using the posted hall ID
    Long hallId = booking.getHall().getId();
    Hall hall = hallRepository.findById(hallId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid hall ID: " + hallId));
    
        if (bookingRepository.existsByHallIdAndDateAndTime(hallId, booking.getDate(), booking.getTime())) {
            model.addAttribute("error", "This hall is already booked for the selected date and time.");
            model.addAttribute("hall", hall);
            model.addAttribute("booking", booking);
            return "booking-form";
        }

        booking.setHall(hall);
        booking.setStatus("Submitted");
        bookingRepository.save(booking);
        model.addAttribute("booking", booking); 
        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String showConfirmation() {
        return "booking-confirmation";
    }
    @GetMapping("/my-bookings")
public String myBookings(Authentication authentication, Model model) {
    String username = authentication.getName();
    List<Booking> myBookings = bookingRepository.findByName(username);
    model.addAttribute("myBookings", myBookings);
    return "my-bookings";
}

@PostMapping("/admin/approve/{id}")
public String approveBooking(@PathVariable Long id) {
    Booking booking = bookingRepository.findById(id).orElse(null);
    if (booking != null) {
        booking.setStatus("Approved");
        bookingRepository.save(booking);
    }
    return "approved"; // Return approved confirmation page
}

@PostMapping("/admin/reject/{id}")
public String rejectBooking(@PathVariable Long id) {
    Booking booking = bookingRepository.findById(id).orElse(null);
    if (booking != null) {
        booking.setStatus("Rejected");
        bookingRepository.save(booking);
    }
    return "rejected"; // Return rejected confirmation page
}

}