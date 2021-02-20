package cinema.movie.reservation;

public interface Result {
    public boolean isSuccess();
    public boolean isFailure();
    public String getFailureReason();
}
