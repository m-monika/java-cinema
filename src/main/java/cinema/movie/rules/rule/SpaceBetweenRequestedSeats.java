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

        for (Map.Entry<Integer, List<RequestedSeat>> requestedSeatsInSector : sectors.entrySet()) {
            if (!checkSector(requestedSeatsInSector.getValue())) {
                return false;
            }
        }

        return true;
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

        for (Map.Entry<Integer, List<RequestedSeat>> requestedSeatsInRow : rows.entrySet()) {
            if (!checkRow(requestedSeatsInRow.getValue())) {
                return false;
            }
        }

        return true;
    }

    private boolean checkRow(List<RequestedSeat> requestedSeats)
    {
        int previousSeatInRow = 0;

        for (RequestedSeat requestedSeat : requestedSeats) {
            if (previousSeatInRow > 0 && !checkIsSpaceBetweenSeats(requestedSeat.getSeatInRow(), previousSeatInRow)) {
                return false;
            }

            previousSeatInRow = requestedSeat.getSeatInRow();
        }

        return true;
    }

    private boolean checkIsSpaceBetweenSeats(int seatInRow, int previousSeatInRow) {
        return ((seatInRow - previousSeatInRow == 1)
                || (seatInRow - previousSeatInRow > this.spaceBetweenSeats));
    }
}
