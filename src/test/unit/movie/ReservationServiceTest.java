package cinema.test.movie;

import cinema.movie.ReservationService;
import cinema.movie.Result;
import cinema.movie.api.RequestedSeat;

import static org.junit.Assert.*;

import cinema.movie.model.Seat;
import org.junit.*;

import cinema.movie.database.Screening;
import cinema.movie.model.Reservation;
import cinema.movie.rules.Database;
import cinema.movie.rules.Rule;
import cinema.movie.rules.rule.NoRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationServiceTest {
    /* TODO change to mock */
    static class ScreeningMock implements Screening {
        boolean returnEmptyReservation;
        boolean canSaveReservation;

        public ScreeningMock(boolean returnEmptyReservation, boolean canSaveReservation) {
            this.returnEmptyReservation = returnEmptyReservation;
            this.canSaveReservation = canSaveReservation;
        }

        @Override
        public boolean saveReservation(Reservation reservation) {
            return this.canSaveReservation;
        }

        @Override
        public Optional<Reservation> getReservation(int idScreening) {
            if (returnEmptyReservation) {
                return Optional.empty();
            } else {
                Seat seat = new Seat(1, 1, 1, true, 0);
                List<Seat> seats = List.of(seat);
                return Optional.of(new Reservation(idScreening, seats));
            }
        }
    }

    static class RuleInterfaceMockFalse implements Rule {
        @Override
        public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
            return false;
        }
    }

    static class RulesMock implements Database {
        boolean canMakeReservation;

        public RulesMock() {
            canMakeReservation = true;
        }
        public RulesMock(boolean canMakeReservation) {
            this.canMakeReservation = canMakeReservation;
        }

        @Override
        public Rule getForMovie(int idMovie) {
            if (canMakeReservation) {
                return new NoRules();
            }

            return new RuleInterfaceMockFalse();
        }
    }

    @Test
    public void tryToMakeReservationButNoSeatsSent() {
        ReservationService reservationService = new ReservationService(
                new RulesMock(),
                new ScreeningMock(true, false)
        );
        List<RequestedSeat> emptyList = new ArrayList<>();
        Result result = reservationService.make(1, emptyList);
        assertFalse(result.isSuccess());
    }

    @Test
    public void tryToMakeReservationButScreenDoNotExist() {
        ReservationService reservationService = new ReservationService(
                new RulesMock(),
                new ScreeningMock(true, false)
        );
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        List<RequestedSeat> requestedSeats = List.of(seat);
        Result result = reservationService.make(1, requestedSeats);
        assertFalse(result.isSuccess());
    }

    @Test
    public void canNotSaveReservation() {
        ReservationService reservationService = new ReservationService(
                new RulesMock(),
                new ScreeningMock(false, false)
        );
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        List<RequestedSeat> requestedSeats = List.of(seat);
        Result result = reservationService.make(1, requestedSeats);
        assertFalse(result.isSuccess());
    }

    @Test
    public void canNotMakeReservationBacauseOfRules() {
        ReservationService reservationService = new ReservationService(
                new RulesMock(false),
                new ScreeningMock(false, true)
        );
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        List<RequestedSeat> requestedSeats = List.of(seat);
        Result result = reservationService.make(1, requestedSeats);
        assertFalse(result.isSuccess());
    }

    @Test
    public void makingReservationSuccess() {
        ReservationService reservationService = new ReservationService(
                new RulesMock(),
                new ScreeningMock(false, true)
        );
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        List<RequestedSeat> requestedSeats = List.of(seat);
        Result result = reservationService.make(1, requestedSeats);
        assertTrue(result.isSuccess());
    }
}