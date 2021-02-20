package cinema.movie.reservation;

import cinema.movie.reservation.Result;

public class SuccessResult implements Result {
    public boolean isSuccess() {
        return true;
    }

    public boolean isFailure() {
        return false;
    }

    public String getFailureReason() {
        return "";
    }
}
