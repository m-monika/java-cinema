package cinema.movie;

import cinema.movie.reservation.RequestedSeat;

import cinema.movie.reservation.SeatModel;

import cinema.movie.database.Screening;
import cinema.movie.reservation.ReservationModel;
import cinema.movie.reservation.ReservationService;
import cinema.movie.reservation.Result;
import cinema.movie.rules.Database;
import cinema.movie.rules.Rule;
import cinema.movie.rules.NoRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationModelServiceTest {
    /* TODO change to mock */
    static class ScreeningMock implements Screening {
        boolean returnEmptyReservation;
        boolean canSaveReservation;

        public ScreeningMock(boolean returnEmptyReservation, boolean canSaveReservation) {
            this.returnEmptyReservation = returnEmptyReservation;
            this.canSaveReservation = canSaveReservation;
        }

        @Override
        public boolean saveReservation(ReservationModel reservationModel) {
            return this.canSaveReservation;
        }

        @Override
        public Optional<ReservationModel> getReservation(int idScreening) {
            if (returnEmptyReservation) {
                return Optional.empty();
            } else {
                SeatModel seatModel = new SeatModel(1, 1, 1, true, 0);
                List<SeatModel> seatModels = List.of(seatModel);
                return Optional.of(new ReservationModel(idScreening, seatModels));
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
        // given
        ReservationService reservationService = new ReservationService(
                new RulesMock(),
                new ScreeningMock(true, false)
        );
        // and
        List<RequestedSeat> emptyList = new ArrayList<>();

        // when
        Result result = reservationService.make(1, emptyList);

        // then
        assertFalse(result.isSuccess());
    }

    @Test
    public void tryToMakeReservationButScreenDoNotExist() {
        // given
        ReservationService reservationService = new ReservationService(
                new RulesMock(),
                new ScreeningMock(true, false)
        );
        // and
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        // and
        List<RequestedSeat> requestedSeats = List.of(seat);

        // when
        Result result = reservationService.make(1, requestedSeats);

        // then
        assertFalse(result.isSuccess());
    }

    @Test
    public void canNotSaveReservation() {
        // given
        ReservationService reservationService = new ReservationService(
                new RulesMock(),
                new ScreeningMock(false, false)
        );
        // and
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        // and
        List<RequestedSeat> requestedSeats = List.of(seat);

        // when
        Result result = reservationService.make(1, requestedSeats);

        // then
        assertFalse(result.isSuccess());
    }

    @Test
    public void canNotMakeReservationBacauseOfRules() {
        // given
        ReservationService reservationService = new ReservationService(
                new RulesMock(false),
                new ScreeningMock(false, true)
        );
        // and
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        // and
        List<RequestedSeat> requestedSeats = List.of(seat);

        // when
        Result result = reservationService.make(1, requestedSeats);

        // then
        assertFalse(result.isSuccess());
    }

    @Test
    public void makingReservationSuccess() {
        // given
        ReservationService reservationService = new ReservationService(
                new RulesMock(),
                new ScreeningMock(false, true)
        );
        // and
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        // and
        List<RequestedSeat> requestedSeats = List.of(seat);

        // when
        Result result = reservationService.make(1, requestedSeats);

        // than
        assertTrue(result.isSuccess());
    }
}