package com.eval.repository;

import com.eval.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository  extends JpaRepository<Room,Long> {
    boolean existsByNumber(String number);
}
