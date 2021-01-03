package cinema.movie;

public interface Result {
    public boolean isSuccess();
    public boolean isFailure();
    public String getFailureReason();
}
