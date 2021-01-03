package cinema.movie.result;

import cinema.movie.Result;

public class Success implements Result {
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
