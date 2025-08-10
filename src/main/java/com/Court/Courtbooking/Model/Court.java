package com.Court.Courtbooking.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "court")
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String type; // e.g. Single, Double
    private Double price;
    private Boolean isAvailable;
    private  Long sportsArenaId; // reference to Auditorium microservice

}
