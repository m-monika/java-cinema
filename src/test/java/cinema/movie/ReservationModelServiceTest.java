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
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationModelServiceTest {
    @Test
    public void tryToMakeReservationButNoSeatsSent() {
        // given
        Database rulesRepositoryMock = Mockito.mock(Database.class);
        Screening screeningMock = Mockito.mock(Screening.class);
        ReservationService reservationService = new ReservationService(
                rulesRepositoryMock,
                screeningMock
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
        Database rulesRepositoryMock = Mockito.mock(Database.class);
        Screening screeningMock = Mockito.mock(Screening.class);
        ReservationService reservationService = new ReservationService(
                rulesRepositoryMock,
                screeningMock
        );
        // and
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        // and
        List<RequestedSeat> requestedSeats = List.of(seat);

        // when
        Mockito.when(screeningMock.getReservation(1)).thenReturn(Optional.empty());
        // and
        Result result = reservationService.make(1, requestedSeats);

        // then
        assertFalse(result.isSuccess());
    }

    @Test
    public void canNotSaveReservation() {
        // given
        Database rulesRepositoryMock = Mockito.mock(Database.class);
        Screening screeningMock = Mockito.mock(Screening.class);
        ReservationService reservationService = new ReservationService(
                rulesRepositoryMock,
                screeningMock
        );
        // and
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        // and
        List<RequestedSeat> requestedSeats = List.of(seat);
        // and
        Mockito.when(rulesRepositoryMock.getForMovie(1)).thenReturn(new NoRules());

        // when
        SeatModel seatModel = new SeatModel(1, 1, 1, true, 0);
        ReservationModel reservationModel = new ReservationModel(1, List.of(seatModel));
        Mockito.when(screeningMock.getReservation(1))
                .thenReturn(Optional.of(reservationModel));
        // and
        Mockito.when(screeningMock.saveReservation(Mockito.mock(ReservationModel.class))).thenReturn(false);
        // and
        Result result = reservationService.make(1, requestedSeats);

        // then
        assertFalse(result.isSuccess());
    }

    @Test
    public void canNotMakeReservationBacauseOfRules() {
        // given
        Database rulesRepositoryMock = Mockito.mock(Database.class);
        Screening screeningMock = Mockito.mock(Screening.class);
        ReservationService reservationService = new ReservationService(
                rulesRepositoryMock,
                screeningMock
        );
        // and
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        // and
        List<RequestedSeat> requestedSeats = List.of(seat);

        // when
        Rule rulesMock = Mockito.mock(Rule.class);
        Mockito.when(rulesRepositoryMock.getForMovie(1)).thenReturn(rulesMock);
        // and
        SeatModel seatModel = new SeatModel(1, 1, 1, true, 0);
        List<SeatModel> seatModels = List.of(seatModel);
        Mockito.when(screeningMock.getReservation(1))
                .thenReturn(Optional.of(new ReservationModel(1, seatModels)));
        // and
        Mockito.when(rulesMock.canMakeReservation(requestedSeats)).thenReturn(false);
        // and
        Result result = reservationService.make(1, requestedSeats);

        // then
        assertFalse(result.isSuccess());
    }

    @Test
    public void makingReservationSuccess() {
        // given
        Database rulesRepositoryMock = Mockito.mock(Database.class);
        Screening screeningMock = Mockito.mock(Screening.class);
        ReservationService reservationService = new ReservationService(
                rulesRepositoryMock,
                screeningMock
        );
        // and
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        // and
        List<RequestedSeat> requestedSeats = List.of(seat);

        // when
        Rule rulesMock = Mockito.mock(Rule.class);
        Mockito.when(rulesRepositoryMock.getForMovie(1)).thenReturn(rulesMock);
        // and
        SeatModel seatModel = new SeatModel(1, 1, 1, true, 0);
        ReservationModel reservationModel = new ReservationModel(1, List.of(seatModel));
        Mockito.when(screeningMock.getReservation(1))
                .thenReturn(Optional.of(reservationModel));
        // and
        Mockito.when(screeningMock.saveReservation(reservationModel)).thenReturn(true);
        // and
        Mockito.when(rulesMock.canMakeReservation(requestedSeats)).thenReturn(true);
        // and
        Result result = reservationService.make(1, requestedSeats);

        // than
        assertTrue(result.isSuccess());
    }
}