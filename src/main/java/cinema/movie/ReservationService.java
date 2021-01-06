package cinema.movie;

import cinema.movie.api.RequestedSeat;
import cinema.movie.database.Screening;
import cinema.movie.model.Reservation;
import cinema.movie.result.Failure;
import cinema.movie.result.Success;
import cinema.movie.rules.Database;
import cinema.movie.rules.Rule;

import java.util.List;
import java.util.Optional;

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

        Optional<Reservation> optionalReservation = screeningDatabase.getReservation(idScreening);

        if (optionalReservation.isEmpty()) {
            return new Failure("Screening you are looking for does not exist.");
        }

        Reservation reservation = optionalReservation.get();

        if (!tryMakeReservation(idScreening, reservation, requestedSeats)) {
            return new Failure("You can not reserve those seats.");
        }

        if (screeningDatabase.saveReservation(reservation)) {
            return new Success();
        }

        return new Failure("Something went wrong, try again later.");
    }

    private boolean validateRequestedSeats(List<RequestedSeat> requestedSeats) {
        return requestedSeats.size() > 0;
    }

    private boolean tryMakeReservation(
            int idScreening,
            Reservation reservation,
            List<RequestedSeat> requestedSeats
    ) {
        Rule rule = this.ruleDatabase.getForMovie(idScreening);

        return reservation.make(rule, requestedSeats);
    }
}