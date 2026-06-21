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
            throw new RuntimeException("La fecha de check-out debe ser posterior al check-in");
        }

        Room room = roomRepository.findById(reservation.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        if(!room.getAvailable()){
            throw new RuntimeException("La habitación no está disponible");
        }

        if(reservationRepository.existsConflict(
                room.getId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate())){
            throw new RuntimeException("La habitación tiene una reserva en conflicto");
        }

        room.setAvailable(false);
        roomRepository.save(room);

        reservation.setRoom(room);
        return reservationRepository.save(reservation);
    }
}
