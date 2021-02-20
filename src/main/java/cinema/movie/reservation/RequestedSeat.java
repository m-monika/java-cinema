package cinema.movie.reservation;

public class RequestedSeat {
    private final int sector;
    private final int row;
    private final int seatInRow;

    public RequestedSeat(int sector, int row, int seatInRow) {
        if (sector < 1) {
            throw new IllegalArgumentException("Sector must be greater than 0");
        }

        if (row < 1) {
            throw new IllegalArgumentException("Row must be greater than 0");
        }

        if (seatInRow < 1) {
            throw new IllegalArgumentException("Seat in row must be greater than 0");
        }

        this.sector = sector;
        this.row = row;
        this.seatInRow = seatInRow;
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
}
