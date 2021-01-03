package cinema.movie.result;

import cinema.movie.Result;

public class Failure implements Result {
    private final String failureReason;

    public Failure(String failureReason) {
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
