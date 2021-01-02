package cinema.movie.rules.rule;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.Rule;

import java.util.List;

public class NoRules implements Rule {
    @Override
    public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
        return true;
    }
}
