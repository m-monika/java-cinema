package cinema.movie.database.screening;

import cinema.movie.api.RequestedSeat;
import cinema.movie.database.Screening;
import cinema.movie.model.Reservation;

import java.util.List;
import java.util.Optional;

public class MySQL implements Screening{
    public Optional<Reservation> getReservation(int idScreening) {
        return Optional.of(new Reservation(idScreening));
    }

    public boolean saveReservation(Reservation reservation) {
        return true;
    }
}