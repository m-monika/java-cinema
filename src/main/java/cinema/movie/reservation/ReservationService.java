package cinema.movie.reservation;

import cinema.movie.database.Screening;
import cinema.movie.rules.Database;
import cinema.movie.rules.Rule;

import java.util.List;

public class ReservationService {
    private final Database ruleDatabase;
    private final Screening screeningDatabase;

    public ReservationService(Database ruleDatabase, Screening screening) {
        this.ruleDatabase = ruleDatabase;
        screeningDatabase = screening;
    }

    public Result make(int idScreening, List<RequestedSeat> requestedSeats) {
        if (!validateRequestedSeats(requestedSeats)) {
            return new FailureResult("You need to choose seats to make a reservation.");
        }

        return screeningDatabase.getReservation(idScreening)
                .map(reservation -> tryMakeReservation(idScreening, reservation, requestedSeats))
                .orElse(new FailureResult("Screening you are looking for does not exist."));
    }

    private boolean validateRequestedSeats(List<RequestedSeat> requestedSeats) {
        return requestedSeats.size() > 0;
    }

    private Result tryMakeReservation(
            int idScreening,
            ReservationModel reservationModel,
            List<RequestedSeat> requestedSeats
    ) {
        Rule rule = this.ruleDatabase.getForMovie(idScreening);

        if (!reservationModel.make(rule, requestedSeats)) {
            return new FailureResult("You can not reserve those seats.");
        }

        if (screeningDatabase.saveReservation(reservationModel)) {
            return new SuccessResult();
        }

        return new FailureResult("Something went wrong, try again later.");
    }
}