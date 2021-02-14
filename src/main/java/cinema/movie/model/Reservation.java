package cinema.movie.model;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.Rule;

import java.util.List;

public class Reservation {
    private final int idScreening;
    private final List<Seat> seats;

    public Reservation(int idScreening, List<Seat> seats) {
        this.idScreening = idScreening;
        this.seats = seats;
    }

    public boolean make(Rule rule, List<RequestedSeat> requestedSeats) {
        boolean result = requestedSeats
                .stream()
                .allMatch(this::reserveSeat);

        return result && rule.canMakeReservation(requestedSeats);
    }

    private boolean reserveSeat(RequestedSeat requestedSeat) {
        Seat foundSeat = seats.stream()
                .filter(seat -> seat.getSeatInRow() == requestedSeat.getSeatInRow())
                .filter(seat -> seat.getRow() == requestedSeat.getRow())
                .filter(seat -> seat.getSector() == requestedSeat.getSector())
                .findAny()
                .orElse(null);

        if (foundSeat == null) {
            return false;
        }

        return foundSeat.reserveSeat();
    }
}
