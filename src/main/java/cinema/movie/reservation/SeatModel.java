package cinema.movie.reservation;

public class SeatModel {
    private final int sector;
    private final int row;
    private final int seatInRow;
    private final int version;
    private boolean isAvailable;

    public SeatModel(
        int sector,
        int row,
        int seatInRow,
        boolean isAvailable,
        int version
    ) {
        this.sector = sector;
        this.row = row;
        this.seatInRow = seatInRow;
        this.isAvailable = isAvailable;
        this.version = version;
    }

    public int getSector() {
        return sector;
    }

    public int getRow() {
        return row;
    }

    public int getSeatInRow() {
        return seatInRow;
    }

    public int getVersion() {
        return version;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean reserveSeat() {
        if (isAvailable) {
            isAvailable = false;

            return true;
        }

        return false;
    }
}