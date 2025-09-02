package com.Court.Courtbooking.Controller;

import com.Court.Courtbooking.Model.AssignCourtsRequest;
import com.Court.Courtbooking.Model.BookingRequest;
import com.Court.Courtbooking.Model.Court;
import com.Court.Courtbooking.Service.CourtService;
import jakarta.transaction.Transactional;
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
    public ResponseEntity<List<Court>> getAvailableCourts(@PathVariable Long arenaId) {
        return ResponseEntity.ok(courtService.getAvailableCourts(arenaId));
    }
    @PostMapping("/add")
    public ResponseEntity<?> createCourt(@RequestBody Court court) {
        return courtService.addCourt(court);
    }
    @PostMapping("/book/{courtNumber}")
    public ResponseEntity<?> bookCourt(@PathVariable String courtNumber){
        return courtService.bookCourt(courtNumber);
    }

    @DeleteMapping("/delete/{courtNumber}")
    public ResponseEntity<?> deleteCourt(@PathVariable String courtNumber){
        return courtService.deleteCourt(courtNumber);
    }
    @PutMapping("/assign")
    public ResponseEntity<Void> assignCourtsToArena(@RequestBody AssignCourtsRequest request) {
        courtService.updateArenaForCourts(request.getArenaId(), request.getCourtIds());
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PostMapping("/book")
    public ResponseEntity<Void> bookCourt(@RequestBody BookingRequest bookingRequest){
        courtService.consumeBookingRequest(bookingRequest);
        return ResponseEntity.noContent().build();
    }

}



