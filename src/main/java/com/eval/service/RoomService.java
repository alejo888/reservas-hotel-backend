package com.eval.service;

import com.eval.model.Room;
import com.eval.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room save(Room room){
        if(roomRepository.existsByNumber(room.getNumber())){
            throw new RuntimeException("Room number already exists");
        }

        room.setAvailable(true);
        return roomRepository.save(room);
    }

    public Room updateAvailability(Long id, Boolean available){
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room id not found"));
        room.setAvailable(available);
        return roomRepository.save(room);
    }
}
