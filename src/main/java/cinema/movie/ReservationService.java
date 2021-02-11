package cinema.movie;

import cinema.movie.api.RequestedSeat;
import cinema.movie.database.Screening;
import cinema.movie.model.Reservation;
import cinema.movie.result.Failure;
import cinema.movie.result.Success;
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
            return new Failure("You need to choose seats to make a reservation.");
        }

        return screeningDatabase.getReservation(idScreening)
                .map(reservation -> tryMakeReservation(idScreening, reservation, requestedSeats))
                .orElse(new Failure("Screening you are looking for does not exist."));
    }

    private boolean validateRequestedSeats(List<RequestedSeat> requestedSeats) {
        return requestedSeats.size() > 0;
    }

    private Result tryMakeReservation(
            int idScreening,
            Reservation reservation,
            List<RequestedSeat> requestedSeats
    ) {
        Rule rule = this.ruleDatabase.getForMovie(idScreening);

        if (!reservation.make(rule, requestedSeats)) {
            return new Failure("You can not reserve those seats.");
        }

        if (screeningDatabase.saveReservation(reservation)) {
            return new Success();
        }

        return new Failure("Something went wrong, try again later.");
    }
}