package cinema.movie.database;

import cinema.movie.model.Reservation;

import java.util.Optional;

public interface Screening {
    public boolean saveReservation(Reservation reservation);
    public Optional<Reservation> getReservation(int idScreening);
}
