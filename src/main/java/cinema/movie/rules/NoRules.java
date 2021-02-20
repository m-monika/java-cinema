package cinema.movie.rules;

import cinema.movie.reservation.RequestedSeat;

import java.util.List;

public class NoRules implements Rule {
    @Override
    public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
        return true;
    }
}
