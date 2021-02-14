package cinema.movie.rules.rule;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.Rule;

import java.util.List;

public class AndRules implements Rule {
    private final List<Rule> rules;

    public AndRules(Rule... rules) {
        this.rules = List.of(rules);
    }

    @Override
    public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
        return rules
                .stream()
                .allMatch(rule -> rule.canMakeReservation(requestedSeats));
    }
}
