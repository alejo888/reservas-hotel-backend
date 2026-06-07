package com.eval.controller;

import com.eval.model.Room;
import com.eval.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @PostMapping
    public ResponseEntity<Room> save(@RequestBody Room room) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(room));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateAvailability(
            @PathVariable Long id,
            @RequestParam Boolean available) {
        return ResponseEntity.ok(roomService.updateAvailability(id, available));
    }
}
