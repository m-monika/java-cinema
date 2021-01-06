package cinema.movie.model;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.Rule;

import java.util.List;

public class Reservation {
    private final int idScreening;

    public Reservation(int idScreening) {
        this.idScreening = idScreening;
    }

    public boolean make(Rule rule, List<RequestedSeat> requestedSeats) {
        for (RequestedSeat requestedSeat : requestedSeats) {
            if (!reserveSeat(requestedSeat)) {
                return false;
            }
        }
        return rule.canMakeReservation(requestedSeats);
    }

    private boolean reserveSeat(RequestedSeat requestedSeat) {
        // @TODO check if seat is available

        return true;
    }
}
