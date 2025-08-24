package com.Court.Courtbooking.Repository;

import com.Court.Courtbooking.Model.Court;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourtRepo extends JpaRepository<Court,Long> {


//    @Query(value = "SELECT * FROM Court WHERE sports_arena_id = :arenaId and is_avialable=true", nativeQuery = true)
//    List<Court> findRoomsByArenaId(@Param("arenaId") Long arenaId);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Court WHERE court_number = :courtNumber", nativeQuery = true)
    int deleteByCourtNumber(@Param("courtNumber") String courtNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Court set is_avialable=false WHERE court_number = :courtNumber", nativeQuery = true)
    int setCourtNumberAvailabilityToFalse(@Param("courtNumber") String courtNumber);

    @Query(value = "SELECT count(*)>0 FROM Court WHERE  court_number=:courtNumber and is_available=true", nativeQuery = true)
    boolean findCourtByCourtNumberAndAvailability( @Param("courtNumber") String courtNumber);
    @Query(value = "SELECT * FROM Court WHERE sports_arena_id = :arenaId and is_available=true", nativeQuery = true)
    List<Court> findAllCourtsByArenaId(@Param("arenaId") Long arenaId);

    @Query(value = "SELECT count(*)>0 FROM Court WHERE  court_number=:courtNumber", nativeQuery = true)
    boolean findCourtByCourtNumber(String courtNumber);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE court SET sports_arena_id = :arenaId WHERE id IN (:ids)", nativeQuery = true)
    int updateArenaForCourts(@Param("arenaId") Long arenaId, @Param("ids") List<Long> ids);
}
