package cinema.test.movie.model;

import cinema.movie.api.RequestedSeat;
import cinema.movie.model.Seat;
import cinema.movie.rules.Rule;
import cinema.movie.model.Reservation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReservationTest {
    static class RuleInterfaceMockTrue implements Rule {
        @Override
        public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
            return true;
        }
    }

    static class RuleInterfaceMockFalse implements Rule {
        @Override
        public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
            return false;
        }
    }

    @Test
    public void canNotMakeReservationBecauseSeatDoesNotExists() {
        List<Seat> seats = new ArrayList<>();

        RequestedSeat requestedSeat = new RequestedSeat(1, 2, 57);
        List<RequestedSeat> requestedSeats = List.of(requestedSeat);

        Reservation reservation = new Reservation(1, seats);
        assertFalse(reservation.make(new RuleInterfaceMockTrue(), requestedSeats));
    }

    @Test
    public void tryToReserveSeatButItIsAnavailable() {
        Seat seat = new Seat(1, 1, 1, false, 0);
        List<Seat> seats = List.of(seat);

        RequestedSeat requestedSeat = new RequestedSeat(1, 1, 1);
        List<RequestedSeat> requestedSeats = List.of(requestedSeat);

        Reservation reservation = new Reservation(1, seats);
        assertFalse(reservation.make(new RuleInterfaceMockTrue(), requestedSeats));
    }

    @Test
    public void tryToReserveAvailableSeatButRulesDontAllowIt() {
        Seat seat = new Seat(1, 1, 1, true, 0);
        List<Seat> seats = List.of(seat);

        RequestedSeat requestedSeat = new RequestedSeat(1, 1, 1);
        List<RequestedSeat> requestedSeats = List.of(requestedSeat);

        Reservation reservation = new Reservation(1, seats);
        assertFalse(reservation.make(new RuleInterfaceMockFalse(), requestedSeats));
    }

    @Test
    public void reserveTwoSeats() {
        Seat seat1 = new Seat(1, 1, 1, true, 0);
        Seat seat2 = new Seat(1, 1, 2, true, 0);
        Seat seat3 = new Seat(1, 1, 3, true, 0);
        List<Seat> seats = List.of(seat1, seat2, seat3);

        RequestedSeat requestedSeat1 = new RequestedSeat(1, 1, 1);
        RequestedSeat requestedSeat2 = new RequestedSeat(1, 1, 2);
        List<RequestedSeat> requestedSeats = List.of(requestedSeat1, requestedSeat2);

        Reservation reservation = new Reservation(1, seats);
        assertTrue(reservation.make(new RuleInterfaceMockTrue(), requestedSeats));
    }
}
