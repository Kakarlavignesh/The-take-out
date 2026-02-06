package com.takeout.controller;

import com.takeout.model.Reservation;
import com.takeout.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        reservation.setStatus("Confirmed"); // Auto-confirm for demo
        return reservationRepository.save(reservation);
    }
}
