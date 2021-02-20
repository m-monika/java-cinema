package cinema.movie.reservation;

class FailureResult implements Result {
    private final String failureReason;

    public FailureResult(String failureReason) {
        this.failureReason = failureReason;
    }

    public boolean isSuccess() {
        return false;
    }

    public boolean isFailure() {
        return true;
    }

    public String getFailureReason() {
        return this.failureReason;
    }
}
