package com.Court.Courtbooking.Service;
import com.Court.Courtbooking.Enum.BookingStatus;
import com.Court.Courtbooking.Model.BookingRequest;
import com.Court.Courtbooking.Model.BookingResponse;
import com.Court.Courtbooking.Model.Court;
import com.Court.Courtbooking.Repository.CourtRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourtService {
    @Autowired
    CourtRepo courtRepo;
    /*
    * The reason you still get an error even after annotating with @Autowired is because Spring cannot inject into a final field unless you use constructor injection.
    * */

    private final KafkaTemplate<String, BookingResponse> kafkaTemplate;
    @Value("${topic.booking-responses}")
    private String bookingResponsesTopic;

    public CourtService(KafkaTemplate<String, BookingResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


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


    @Transactional
    public void updateArenaForCourts(Long arenaId, List<Long> courtIds) {
        // Bulk update for existing courts
        int updated = courtRepo.updateArenaForCourts(arenaId, courtIds);

        if (updated == 0) {
            // optionally log or throw exception
            throw new RuntimeException("No courts were updated. Court IDs may be invalid.");
        }

        // Add new courts if some IDs don’t exist
        for (Long id : courtIds) {
            if (!courtRepo.existsById(id)) {
                Court newCourt = new Court();
                newCourt.setId(id); // only if IDs are managed manually
                newCourt.setSportsArenaId(arenaId);
                courtRepo.save(newCourt);
            }
        }
    }

    @KafkaListener(topics = "${topic.booking-requests}", groupId = "court-service", containerFactory = "bookingRequestListenerFactory")
    public void consumeBookingRequest(BookingRequest bookingRequest) {
        System.out.println("📥 CourtService got booking request: " + bookingRequest);

        BookingResponse response = new BookingResponse();
        response.setBookingId(bookingRequest.getBookingId());
        Optional<Court> courtObject=courtRepo.findById(bookingRequest.getCourtId());

        if (courtObject.isPresent() && Boolean.TRUE.equals(courtObject.get().getIsAvailable())) {
            Court court = courtObject.get();
            court.setIsAvailable(false); // mark reserved
            courtRepo.save(court);
            response.setStatus(BookingStatus.CONFIRMED);
        } else {
            response.setStatus(BookingStatus.REJECTED);
        }

        kafkaTemplate.send(bookingResponsesTopic, bookingRequest.getBookingId(), response);
        System.out.println("📤 CourtService sent response: " + response);

    }
}
