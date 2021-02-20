package cinema.movie.reservation;

import cinema.movie.rules.Rule;

import java.util.List;

public class ReservationModel {
    private final int idScreening;
    private final List<SeatModel> seatModels;

    public ReservationModel(int idScreening, List<SeatModel> seatModels) {
        this.idScreening = idScreening;
        this.seatModels = seatModels;
    }

    public boolean make(Rule rule, List<RequestedSeat> requestedSeats) {
        boolean result = requestedSeats
                .stream()
                .allMatch(this::reserveSeat);

        return result && rule.canMakeReservation(requestedSeats);
    }

    private boolean reserveSeat(RequestedSeat requestedSeat) {
        SeatModel foundSeatModel = seatModels.stream()
                .filter(seatModel -> seatModel.getSeatInRow() == requestedSeat.getSeatInRow())
                .filter(seatModel -> seatModel.getRow() == requestedSeat.getRow())
                .filter(seatModel -> seatModel.getSector() == requestedSeat.getSector())
                .findAny()
                .orElse(null);

        if (foundSeatModel == null) {
            return false;
        }

        return foundSeatModel.reserveSeat();
    }
}
