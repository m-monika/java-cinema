package cinema.movie.rules.rule;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.Rule;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AndRulesTest {
    public static List<RequestedSeat> requestedSeats;

    static class RuleInterfaceMockTrue implements Rule {
        @Override
        public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
            return true;
        }
    }
    static class RuleInterfaceMockFalse implements Rule {
        @Override
        public boolean canMakeReservation(List<RequestedSeat> requestedSeats) {
            return false;
        }
    }

    @BeforeEach
    public void setUp() {
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        RequestedSeat secondSeat = new RequestedSeat(1, 1, 2);
        requestedSeats = List.of(seat, secondSeat);
    }

    @Test
    public void canMakeReservationWhenTrueAndFalseReturned() {
        //given
        RuleInterfaceMockTrue ruleTrue = new RuleInterfaceMockTrue();
        RuleInterfaceMockFalse ruleFalse = new RuleInterfaceMockFalse();
        AndRules andRules = new AndRules(ruleTrue, ruleFalse);

        //expect
        assertFalse(andRules.canMakeReservation(requestedSeats));
    }

    @Test
    public void canMakeReservationWhenTrueReturned() {
        //given
        RuleInterfaceMockTrue ruleTrue = new RuleInterfaceMockTrue();
        AndRules andRules = new AndRules(ruleTrue);

        //expect
        assertTrue(andRules.canMakeReservation(requestedSeats));
    }

    @Test
    public void canMakeReservationWhenFalseReturned() {
        //given
        RuleInterfaceMockFalse ruleFalse = new RuleInterfaceMockFalse();
        AndRules andRules = new AndRules(ruleFalse);

        //expect
        assertFalse(andRules.canMakeReservation(requestedSeats));
    }
}
