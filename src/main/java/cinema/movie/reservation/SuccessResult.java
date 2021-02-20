package cinema.movie.reservation;

class SuccessResult implements Result {
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
