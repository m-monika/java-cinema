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
        Map<Integer,Map<Integer,List<Integer>>> groupedSeats = groupSeats(requestedSeats);

        return checkGroupedSeats(groupedSeats);
    }

    private Map<Integer,Map<Integer,List<Integer>>> groupSeats(List<RequestedSeat> requestedSeats){
        Map<Integer,Map<Integer,List<Integer>>> allRows = new HashMap();

        /*
         * @TODO REFACTOR!
         */
        for (RequestedSeat requestedSeat : requestedSeats) {
            if (!allRows.containsKey(requestedSeat.getSector())) {
                Map<Integer,List<Integer>> row = new HashMap();
                List<Integer> seats = new ArrayList();
                seats.add(requestedSeat.getSeatInRow());
                row.put(requestedSeat.getRow(), seats);
                allRows.put(requestedSeat.getSector(), row);
            } else if (!allRows.get(requestedSeat.getSector()).containsKey(requestedSeat.getRow())) {
                List<Integer> seats = new ArrayList();
                seats.add(requestedSeat.getSeatInRow());
                allRows.get(requestedSeat.getSector()).put(requestedSeat.getRow(), seats);
            } else {
                allRows.get(requestedSeat.getSector()).get(requestedSeat.getRow()).add(requestedSeat.getSeatInRow());
            }
        }

        return allRows;
    }

    private boolean checkGroupedSeats(Map<Integer,Map<Integer,List<Integer>>> groupedSeats) {
        for(Map.Entry<Integer,Map<Integer,List<Integer>>> sector : groupedSeats.entrySet()) {
            if (!checkRowsInSector(sector)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkRowsInSector(Map.Entry<Integer,Map<Integer,List<Integer>>> rows) {
        for(Map.Entry<Integer,List<Integer>> row : rows.getValue().entrySet()) {
            if (!checkSeatsInRow(row.getValue())) {
                return false;
            }
        }

        return true;
    }

    private boolean checkSeatsInRow(List<Integer> seatsInRow) {
        Integer previousSeatInRow = 0;
        for (Integer seat : seatsInRow) {
            if (previousSeatInRow > 0 && !checkIsSpaceBetweenSeats(seat, previousSeatInRow)) {
                return false;
            }

            previousSeatInRow = seat;
        }

        return true;
    }

    private boolean checkIsSpaceBetweenSeats(Integer seatInRow, Integer previousSeatInRow) {
        return ((seatInRow - previousSeatInRow == 1)
                || (seatInRow - previousSeatInRow > this.spaceBetweenSeats));
    }
}
