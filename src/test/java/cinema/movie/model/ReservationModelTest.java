package cinema.movie.model;

import cinema.movie.reservation.RequestedSeat;
import cinema.movie.reservation.ReservationModel;
import cinema.movie.reservation.SeatModel;
import cinema.movie.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReservationModelTest {
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
        List<SeatModel> seatModels = new ArrayList<>();
        //and
        RequestedSeat requestedSeat = new RequestedSeat(1, 2, 57);
        //and
        List<RequestedSeat> requestedSeats = List.of(requestedSeat);
        //and
        ReservationModel reservationModel = new ReservationModel(1, seatModels);

        //expect
        assertFalse(reservationModel.make(new RuleInterfaceMockTrue(), requestedSeats));
    }

    @Test
    public void tryToReserveSeatButItIsAnavailable() {
        //given
        SeatModel seatModel = new SeatModel(1, 1, 1, false, 0);
        //and
        List<SeatModel> seatModels = List.of(seatModel);
        //and
        RequestedSeat requestedSeat = new RequestedSeat(1, 1, 1);
        //and
        List<RequestedSeat> requestedSeats = List.of(requestedSeat);
        //and
        ReservationModel reservationModel = new ReservationModel(1, seatModels);

        //expect
        assertFalse(reservationModel.make(new RuleInterfaceMockTrue(), requestedSeats));
    }

    @Test
    public void tryToReserveAvailableSeatButRulesDontAllowIt() {
        //given
        SeatModel seatModel = new SeatModel(1, 1, 1, true, 0);
        //and
        List<SeatModel> seatModels = List.of(seatModel);
        //and
        RequestedSeat requestedSeat = new RequestedSeat(1, 1, 1);
        //and
        List<RequestedSeat> requestedSeats = List.of(requestedSeat);
        //and
        ReservationModel reservationModel = new ReservationModel(1, seatModels);

        //expect
        assertFalse(reservationModel.make(new RuleInterfaceMockFalse(), requestedSeats));
    }

    @Test
    public void reserveTwoSeats() {
        //given
        SeatModel seatModel1 = new SeatModel(1, 1, 1, true, 0);
        //and
        SeatModel seatModel2 = new SeatModel(1, 1, 2, true, 0);
        //and
        SeatModel seatModel3 = new SeatModel(1, 1, 3, true, 0);
        //and
        List<SeatModel> seatModels = List.of(seatModel1, seatModel2, seatModel3);
        //and
        RequestedSeat requestedSeat1 = new RequestedSeat(1, 1, 1);
        //and
        RequestedSeat requestedSeat2 = new RequestedSeat(1, 1, 2);
        //and
        List<RequestedSeat> requestedSeats = List.of(requestedSeat1, requestedSeat2);
        //and
        ReservationModel reservationModel = new ReservationModel(1, seatModels);

        //expect
        assertTrue(reservationModel.make(new RuleInterfaceMockTrue(), requestedSeats));
    }
}
