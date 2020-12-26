package cinema.movie.rules.rule;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.Rule;

import java.util.List;

public class AndRules implements Rule {
    private final Rule[] rules;

    public AndRules(Rule... rules) {
        this.rules = rules;
    }

    @Override
    public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
        for (Rule rule : rules) {
            if (!rule.canMakeReservation(requestedSeats)) {
                return false;
            }
        }

        return true;
    }
}
