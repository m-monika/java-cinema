package cinema.movie.database.screening;

import cinema.movie.database.Screening;
import cinema.movie.model.Reservation;
import cinema.movie.model.Seat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQL implements Screening{
    public Optional<Reservation> getReservation(int idScreening) {
        List<Seat> seats = new ArrayList<>();
        return Optional.of(new Reservation(idScreening, seats));
    }

    public boolean saveReservation(Reservation reservation) {
        return true;
    }
}