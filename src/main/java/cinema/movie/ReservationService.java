package cinema.movie;

import cinema.movie.api.RequestedSeat;
import cinema.movie.result.Failure;
import cinema.movie.result.Success;
import cinema.movie.rules.Database;
import cinema.movie.rules.Rule;

import java.util.List;

public class ReservationService {
    private final Database ruleDatabase;

    public ReservationService(Database ruleDatabase) {
        this.ruleDatabase = ruleDatabase;
    }

    public Result make(int idScreening, List<RequestedSeat> requestedSeats) {
        if (!validateRequestedSeats(requestedSeats)) {
            return new Failure("You need to choose seats to make a reservation.");
        }

        // get reservation

        if (!tryMakeReservation(idScreening, requestedSeats)) {
            return new Failure("You can not reserve those seats.");
        }

        // save reservation

        return new Failure("Something went wrong, try again later.");
    }

    private boolean validateRequestedSeats(List<RequestedSeat> requestedSeats) {
        return requestedSeats.size() > 0;
    }

    private boolean tryMakeReservation(
            int idScreening,
            List<RequestedSeat> requestedSeats
    ) {
        Rule rule = this.ruleDatabase.getForMovie(idScreening);

        // try make reservation on model

        return true;
    }
}