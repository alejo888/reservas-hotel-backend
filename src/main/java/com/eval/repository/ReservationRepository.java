package com.eval.repository;

import com.eval.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("""
          SELECT COUNT(r) > 0 FROM Reservation r
          WHERE r.room.id = :roomId
          AND r.checkInDate < :checkOut
          AND r.checkOutDate > :checkIn
      """)
    boolean existsConflict(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );
}
