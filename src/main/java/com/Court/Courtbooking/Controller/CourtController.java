package com.Court.Courtbooking.Controller;

import com.Court.Courtbooking.Model.Court;
import com.Court.Courtbooking.Service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courts")
public class CourtController {
    @Autowired
    private CourtService courtService;

    @GetMapping("/{arenaId}")
    public ResponseEntity<List<Court>> getRoomsBySportsArena(@PathVariable Long hotelId) {
        return ResponseEntity.ok(courtService.getAvailableCourts(hotelId));
    }
    @PostMapping("/add")
    public ResponseEntity<?> createCourt(@RequestBody Court court) {
        return courtService.addCourt(court);
    }

    @DeleteMapping("/delete/{courtNumber}")
    public ResponseEntity<?> deleteCourt(@PathVariable String courtNumber){
        return courtService.deleteCourt(courtNumber);
    }
}



