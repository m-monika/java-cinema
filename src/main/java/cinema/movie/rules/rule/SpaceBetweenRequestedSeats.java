package cinema.movie.rules.rule;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.Rule;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class SpaceBetweenRequestedSeats implements Rule {
    private final int spaceBetweenSeats;

    public SpaceBetweenRequestedSeats(int spaceBetweenSeats) {
        this.spaceBetweenSeats = spaceBetweenSeats;
    }

    @Override
    public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
        return checkAll(requestedSeats);
    }

    private boolean checkAll(List<RequestedSeat> requestedSeats)
    {
        Map<Integer,List<RequestedSeat>> sectors = new HashMap<>();

        for (RequestedSeat requestedSeat : requestedSeats) {
            if (!sectors.containsKey(requestedSeat.getSector())) {
                List<RequestedSeat> seats = new ArrayList<>();
                seats.add(requestedSeat);
                sectors.put(requestedSeat.getSector(),seats);
            } else {
                sectors.get(requestedSeat.getSector()).add(requestedSeat);
            }
        }

        return sectors.entrySet()
                .stream()
                .allMatch(requestedSeatsInSector -> checkSector(requestedSeatsInSector.getValue()));
    }

    private boolean checkSector(List<RequestedSeat> requestedSeats)
    {
        Map<Integer,List<RequestedSeat>> rows = new HashMap<>();

        for (RequestedSeat requestedSeat : requestedSeats) {
            if (!rows.containsKey(requestedSeat.getRow())) {
                List<RequestedSeat> seats = new ArrayList<>();
                seats.add(requestedSeat);
                rows.put(requestedSeat.getRow(), seats);
            } else {
                rows.get(requestedSeat.getRow()).add(requestedSeat);
            }
        }

        return rows.entrySet()
                .stream()
                .allMatch(requestedSeatsInRow -> checkRow(requestedSeatsInRow.getValue()));
    }

    private boolean checkRow(List<RequestedSeat> requestedSeats)
    {
        int previousSeatInRow = 0;

        for (RequestedSeat requestedSeat : requestedSeats) {
            if (!checkIsSpaceBetweenSeats(requestedSeat.getSeatInRow(), previousSeatInRow)) {
                return false;
            }

            previousSeatInRow = requestedSeat.getSeatInRow();
        }

        return true;
    }

    private boolean checkIsSpaceBetweenSeats(int seatInRow, int previousSeatInRow) {
        return (previousSeatInRow < 1
                || (seatInRow - previousSeatInRow == 1)
                || (seatInRow - previousSeatInRow > this.spaceBetweenSeats));
    }
}
