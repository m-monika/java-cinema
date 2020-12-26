package cinema.test.movie.rules.rule;

import cinema.movie.api.RequestedSeat;
import cinema.movie.rules.Rule;
import cinema.movie.rules.rule.AndRules;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AndRulesTest {
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

    @Test
    void canMakeReservationWhenTrueAndFalseReturned() {
        /* @TODO, pytania: 1. @Before (przypisanie zmiennej) 2. Mockito */
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        RequestedSeat secondSeat = new RequestedSeat(1, 1, 2);
        List<RequestedSeat> requestedSeats = List.of(seat, secondSeat);
        RuleInterfaceMockTrue ruleTrue = new RuleInterfaceMockTrue();
        RuleInterfaceMockFalse ruleFalse = new RuleInterfaceMockFalse();
        AndRules andRules = new AndRules(ruleTrue, ruleFalse);
        assertFalse(andRules.canMakeReservation(requestedSeats));
    }

    @Test
    void canMakeReservationWhenTrueReturned() {
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        RequestedSeat secondSeat = new RequestedSeat(1, 1, 2);
        List<RequestedSeat> requestedSeats = List.of(seat, secondSeat);
        RuleInterfaceMockTrue ruleTrue = new RuleInterfaceMockTrue();
        AndRules andRules = new AndRules(ruleTrue);
        assertTrue(andRules.canMakeReservation(requestedSeats));
    }

    @Test
    void canMakeReservationWhenFalseReturned() {
        RequestedSeat seat = new RequestedSeat(1, 1, 1);
        RequestedSeat secondSeat = new RequestedSeat(1, 1, 2);
        List<RequestedSeat> requestedSeats = List.of(seat, secondSeat);
        RuleInterfaceMockFalse ruleFalse = new RuleInterfaceMockFalse();
        AndRules andRules = new AndRules(ruleFalse);
        assertFalse(andRules.canMakeReservation(requestedSeats));
    }
}
