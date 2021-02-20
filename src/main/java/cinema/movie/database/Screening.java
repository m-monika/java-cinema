package cinema.movie.database;

import cinema.movie.reservation.ReservationModel;

import java.util.Optional;

public interface Screening {
    public boolean saveReservation(ReservationModel reservationModel);
    public Optional<ReservationModel> getReservation(int idScreening);
}
