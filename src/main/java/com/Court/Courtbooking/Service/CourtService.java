package com.Court.Courtbooking.Service;
import com.Court.Courtbooking.Model.Court;
import com.Court.Courtbooking.Repository.CourtRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourtService {
    @Autowired
    CourtRepo courtRepo;

    public List<Court> getAvailableCourts(Long arenaId) {
        return courtRepo.findAllCourtsByArenaId(arenaId);
    }

    public ResponseEntity<?> addCourt(Court court) {
          courtRepo.save(court);
          return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCourt(String courtNumber) {

        if(courtRepo.deleteByCourtNumber(courtNumber)>=0){
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity("Court Number doesnot exist",HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> bookCourt(String courtNumber) {
        if(courtRepo.findCourtByCourtNumberAndAvailability(courtNumber)){
            courtRepo.setCourtNumberAvailabilityToFalse(courtNumber);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity("Court Number is not available",HttpStatus.NOT_FOUND);
    }
}
