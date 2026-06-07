package com.eval.controller;

import com.eval.model.Reservation;
import com.eval.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll(){
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> save(@RequestBody Reservation reservation){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.save(reservation));
    }
}
