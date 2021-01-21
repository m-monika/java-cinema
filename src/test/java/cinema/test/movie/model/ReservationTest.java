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
        //given
        List<Seat> seats = new ArrayList<>();
        //and
        RequestedSeat requestedSeat = new RequestedSeat(1, 2, 57);
        //and
        List<RequestedSeat> requestedSeats = List.of(requestedSeat);
        //and
        Reservation reservation = new Reservation(1, seats);

        //expect
        assertFalse(reservation.make(new RuleInterfaceMockTrue(), requestedSeats));
    }

    @Test
    public void tryToReserveSeatButItIsAnavailable() {
        //given
        Seat seat = new Seat(1, 1, 1, false, 0);
        //and
        List<Seat> seats = List.of(seat);
        //and
        RequestedSeat requestedSeat = new RequestedSeat(1, 1, 1);
        //and
        List<RequestedSeat> requestedSeats = List.of(requestedSeat);
        //and
        Reservation reservation = new Reservation(1, seats);

        //expect
        assertFalse(reservation.make(new RuleInterfaceMockTrue(), requestedSeats));
    }

    @Test
    public void tryToReserveAvailableSeatButRulesDontAllowIt() {
        //given
        Seat seat = new Seat(1, 1, 1, true, 0);
        //and
        List<Seat> seats = List.of(seat);
        //and
        RequestedSeat requestedSeat = new RequestedSeat(1, 1, 1);
        //and
        List<RequestedSeat> requestedSeats = List.of(requestedSeat);
        //and
        Reservation reservation = new Reservation(1, seats);

        //expect
        assertFalse(reservation.make(new RuleInterfaceMockFalse(), requestedSeats));
    }

    @Test
    public void reserveTwoSeats() {
        //given
        Seat seat1 = new Seat(1, 1, 1, true, 0);
        //and
        Seat seat2 = new Seat(1, 1, 2, true, 0);
        //and
        Seat seat3 = new Seat(1, 1, 3, true, 0);
        //and
        List<Seat> seats = List.of(seat1, seat2, seat3);
        //and
        RequestedSeat requestedSeat1 = new RequestedSeat(1, 1, 1);
        //and
        RequestedSeat requestedSeat2 = new RequestedSeat(1, 1, 2);
        //and
        List<RequestedSeat> requestedSeats = List.of(requestedSeat1, requestedSeat2);
        //and
        Reservation reservation = new Reservation(1, seats);

        //expect
        assertTrue(reservation.make(new RuleInterfaceMockTrue(), requestedSeats));
    }
}
