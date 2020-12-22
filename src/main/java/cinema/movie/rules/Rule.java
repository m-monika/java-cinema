package cinema.movie.rules;

import cinema.movie.api.RequestedSeat;

import java.util.List;

public interface Rule {
    public boolean canMakeReservation(List<RequestedSeat> requestedSeats);
}
