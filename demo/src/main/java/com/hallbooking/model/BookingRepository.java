package com.hallbooking.model;


import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByHallIdAndDateAndTime(Long hallId, LocalDate date, LocalTime time);
    List<Booking> findByName(String name);

}
