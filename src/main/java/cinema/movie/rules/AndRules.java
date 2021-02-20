package cinema.movie.rules;

import cinema.movie.reservation.RequestedSeat;

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
