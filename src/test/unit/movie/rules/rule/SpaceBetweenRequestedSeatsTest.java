package cinema.test.movie.rules.rule;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.rule.SpaceBetweenRequestedSeats;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SpaceBetweenRequestedSeatsTest {
    @Test
    void canNotMakeReservationOnlyOneSeatBetweenButTwoExpected() {
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        RequestedSeat secondSeat = new RequestedSeat(1, 1, 3);
        List<RequestedSeat> requestedSeats = List.of(seat, secondSeat);
        SpaceBetweenRequestedSeats reservation = new SpaceBetweenRequestedSeats(2);
        assertFalse(reservation.canMakeReservation(requestedSeats));
    }

    @Test
    void canMakeReservationForTwoSeatsNextToEachOther() {
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        RequestedSeat secondSeat = new RequestedSeat(1, 1, 2);
        List<RequestedSeat> requestedSeats = List.of(seat, secondSeat);
        SpaceBetweenRequestedSeats reservation = new SpaceBetweenRequestedSeats(2);
        assertTrue(reservation.canMakeReservation(requestedSeats));
    }

    @Test
    void canMakeReservationForSeatsInDifferentRowAndSector() {
        RequestedSeat firstSeatInFirstRow = new RequestedSeat(1, 1, 1);
        RequestedSeat secondSeatInFirstRow = new RequestedSeat(1, 1, 2);
        RequestedSeat firstSeatInSecondRow = new RequestedSeat(1, 2, 1);
        RequestedSeat secondSeatInSecondRow = new RequestedSeat(1, 2, 4);
        RequestedSeat firstSeatInAnotherSector = new RequestedSeat(2, 1, 1);
        RequestedSeat secondSeatInAnotherSector = new RequestedSeat(2, 1, 2);
        List<RequestedSeat> requestedSeats = List.of(
                firstSeatInFirstRow,
                secondSeatInFirstRow,
                firstSeatInSecondRow,
                secondSeatInSecondRow,
                firstSeatInAnotherSector,
                secondSeatInAnotherSector
        );
        SpaceBetweenRequestedSeats reservation = new SpaceBetweenRequestedSeats(2);
        assertTrue(reservation.canMakeReservation(requestedSeats));
    }

    @Test
    void canNotMakeReservationForSeatsInDifferentRowWhenInSecondIsNoRequiredSpace() {
        RequestedSeat firstSeatInFirstRow = new RequestedSeat(1, 1, 1);
        RequestedSeat secondSeatInFirstRow = new RequestedSeat(1, 1, 2);
        RequestedSeat firstSeatInSecondRow = new RequestedSeat(1, 2, 2);
        RequestedSeat secondSeatInSecondRow = new RequestedSeat(1, 2, 4);
        List<RequestedSeat> requestedSeats = List.of(
                firstSeatInFirstRow,
                secondSeatInFirstRow,
                firstSeatInSecondRow,
                secondSeatInSecondRow
        );
        SpaceBetweenRequestedSeats reservation = new SpaceBetweenRequestedSeats(2);
        assertFalse(reservation.canMakeReservation(requestedSeats));
    }
}
