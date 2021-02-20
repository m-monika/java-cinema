package cinema.movie.rules;

import cinema.movie.reservation.RequestedSeat;

import java.util.List;

public interface Rule {
    boolean canMakeReservation(List<RequestedSeat> requestedSeats);
}
