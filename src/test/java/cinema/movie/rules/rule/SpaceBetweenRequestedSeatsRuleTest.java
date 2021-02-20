package cinema.movie.rules.rule;

import cinema.movie.reservation.RequestedSeat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cinema.movie.rules.SpaceBetweenRequestedSeatsRule;
import org.junit.jupiter.api.Test;

public class SpaceBetweenRequestedSeatsRuleTest {
    @Test
    void canNotMakeReservationOnlyOneSeatBetweenButTwoExpected() {
        //given
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        //and
        RequestedSeat secondSeat = new RequestedSeat(1, 1, 3);
        //and
        List<RequestedSeat> requestedSeats = List.of(seat, secondSeat);

        //when
        SpaceBetweenRequestedSeatsRule reservation = new SpaceBetweenRequestedSeatsRule(2);

        //then
        assertFalse(reservation.canMakeReservation(requestedSeats));
    }

    @Test
    void canMakeReservationForTwoSeatsNextToEachOther() {
        //given
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        //and
        RequestedSeat secondSeat = new RequestedSeat(1, 1, 2);
        //and
        List<RequestedSeat> requestedSeats = List.of(seat, secondSeat);

        //when
        SpaceBetweenRequestedSeatsRule reservation = new SpaceBetweenRequestedSeatsRule(2);

        //then
        assertTrue(reservation.canMakeReservation(requestedSeats));
    }

    @Test
    void canMakeReservationForSeatsInDifferentRowAndSector() {
        //given
        RequestedSeat firstSeatInFirstRow = new RequestedSeat(1, 1, 1);
        //and
        RequestedSeat secondSeatInFirstRow = new RequestedSeat(1, 1, 2);
        //and
        RequestedSeat firstSeatInSecondRow = new RequestedSeat(1, 2, 1);
        //and
        RequestedSeat secondSeatInSecondRow = new RequestedSeat(1, 2, 4);
        //and
        RequestedSeat firstSeatInAnotherSector = new RequestedSeat(2, 1, 1);
        //and
        RequestedSeat secondSeatInAnotherSector = new RequestedSeat(2, 1, 2);
        //and
        List<RequestedSeat> requestedSeats = List.of(
                firstSeatInFirstRow,
                secondSeatInFirstRow,
                firstSeatInSecondRow,
                secondSeatInSecondRow,
                firstSeatInAnotherSector,
                secondSeatInAnotherSector
        );

        //when
        SpaceBetweenRequestedSeatsRule reservation = new SpaceBetweenRequestedSeatsRule(2);

        //then
        assertTrue(reservation.canMakeReservation(requestedSeats));
    }

    @Test
    void canNotMakeReservationForSeatsInDifferentRowWhenInSecondIsNoRequiredSpace() {
        //given
        RequestedSeat firstSeatInFirstRow = new RequestedSeat(1, 1, 1);
        //and
        RequestedSeat secondSeatInFirstRow = new RequestedSeat(1, 1, 2);
        //and
        RequestedSeat firstSeatInSecondRow = new RequestedSeat(1, 2, 2);
        //and
        RequestedSeat secondSeatInSecondRow = new RequestedSeat(1, 2, 4);
        //and
        List<RequestedSeat> requestedSeats = List.of(
                firstSeatInFirstRow,
                secondSeatInFirstRow,
                firstSeatInSecondRow,
                secondSeatInSecondRow
        );

        //when
        SpaceBetweenRequestedSeatsRule reservation = new SpaceBetweenRequestedSeatsRule(2);

        //then
        assertFalse(reservation.canMakeReservation(requestedSeats));
    }
}
