package cinema.movie.database.screening;

import cinema.movie.database.Screening;
import cinema.movie.reservation.ReservationModel;
import cinema.movie.reservation.SeatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQL implements Screening{
    public Optional<ReservationModel> getReservation(int idScreening) {
        List<SeatModel> seatModels = new ArrayList<>();
        return Optional.of(new ReservationModel(idScreening, seatModels));
    }

    public boolean saveReservation(ReservationModel reservationModel) {
        return true;
    }
}