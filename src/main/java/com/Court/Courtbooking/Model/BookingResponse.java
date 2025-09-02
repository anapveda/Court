package com.Court.Courtbooking.Model;

import com.Court.Courtbooking.Enum.BookingStatus;
import lombok.Data;

@Data
public class BookingResponse {
    private String bookingId;
    private BookingStatus status;
}
