package com.Court.Courtbooking.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignCourtsRequest {
    private Long arenaId;
    private List<Long> courtIds;
}
