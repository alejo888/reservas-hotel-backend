package com.eval.service;

import com.eval.model.Reservation;
import com.eval.model.Room;
import com.eval.repository.ReservationRepository;
import com.eval.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(Reservation reservation){
        if(reservation.getCheckOutDate().isBefore(reservation.getCheckInDate()) ||
                reservation.getCheckOutDate().isEqual(reservation.getCheckInDate())){
            throw new RuntimeException("Check-out must be after check-in");
        }

        Room room = roomRepository.findById(reservation.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if(!room.getAvailable()){
            throw new RuntimeException("Room is not available");
        }

        if(reservationRepository.existsConflict(
                room.getId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate())){
            throw new RuntimeException("Room has a conflicting reservation");
        }

        room.setAvailable(false);
        roomRepository.save(room);

        reservation.setRoom(room);
        return reservationRepository.save(reservation);
    }
}
