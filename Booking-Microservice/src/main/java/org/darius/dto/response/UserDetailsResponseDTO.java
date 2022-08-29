package org.darius.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDTO {
    private String username;
    private Integer age;
    private String firstName;
    private String gender;
    private String phoneNumber;
    List<BookingResponse> bookingResponses = new ArrayList<>();

    public void addBookingResponse(Long bookingId, Date date){
        this.bookingResponses.add(new BookingResponse(bookingId, date));
    }
    @Getter
    class BookingResponse{
        private Long bookingId;
        private Date date;

        public BookingResponse(){}

        public BookingResponse(Long bookingId, Date date) {
            this.bookingId = bookingId;
            this.date = date;
        }
    }
}
