package cinema.test.movie.rules.rule;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.Rule;
import cinema.movie.rules.rule.AndRules;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.*;

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

    @BeforeClass
    public static void setUp() {
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        RequestedSeat secondSeat = new RequestedSeat(1, 1, 2);
        requestedSeats = List.of(seat, secondSeat);
    }

    @Test
    public void canMakeReservationWhenTrueAndFalseReturned() {
        RuleInterfaceMockTrue ruleTrue = new RuleInterfaceMockTrue();
        RuleInterfaceMockFalse ruleFalse = new RuleInterfaceMockFalse();
        AndRules andRules = new AndRules(ruleTrue, ruleFalse);
        assertFalse(andRules.canMakeReservation(requestedSeats));
    }

    @Test
    public void canMakeReservationWhenTrueReturned() {
        RuleInterfaceMockTrue ruleTrue = new RuleInterfaceMockTrue();
        AndRules andRules = new AndRules(ruleTrue);
        assertTrue(andRules.canMakeReservation(requestedSeats));
    }

    @Test
    public void canMakeReservationWhenFalseReturned() {
        RuleInterfaceMockFalse ruleFalse = new RuleInterfaceMockFalse();
        AndRules andRules = new AndRules(ruleFalse);
        assertFalse(andRules.canMakeReservation(requestedSeats));
    }
}
