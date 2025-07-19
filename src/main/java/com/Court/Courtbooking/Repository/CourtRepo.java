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


    @Query(value = "SELECT * FROM Court WHERE sports_arena_id = :arenaId", nativeQuery = true)
    List<Court> findRoomsByArenaId(@Param("arenaId") Long arenaId);

    @Query(value = "SELECT count(*)>0 FROM Court WHERE  court_number=:courtNumber and sports_arena_id = :sportsArenaId", nativeQuery = true)
    boolean findCourtByCourtNameAndArenaId(@Param("courtNumber")String courtNumber, @Param("sportsArenaId")Long sportsArenaId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Court WHERE court_number = :courtNumber", nativeQuery = true)
    int deleteByCourtNumber(@Param("courtNumber") String courtNumber);
}
