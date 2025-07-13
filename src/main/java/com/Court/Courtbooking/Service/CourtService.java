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
        return courtRepo.findRoomsByArenaId(arenaId);
    }

    public ResponseEntity<?> addCourt(Court court) {
        Court c=new Court();
       if(!courtRepo.findCourtByCourtNameAndArenaId(court.getCourtNumber(),court.getSportsArenaId())){
          c.setCourtNumber(court.getCourtNumber());
          c.setType(court.getType());
          c.setPrice(court.getPrice());
          c.setIsAvailable(court.getIsAvailable());
          c.setSportsArenaId(court.getSportsArenaId());
          courtRepo.save(c);
          return ResponseEntity.ok(HttpStatus.OK);
       }
       return new ResponseEntity("Court Number already exist",HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> deleteCourt(String courtNumber) {

        if(courtRepo.deleteByCourtNumber(courtNumber)>=0){
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity("Court Number doesnot exist",HttpStatus.FORBIDDEN);
    }
}
