package io.github.shangor.state;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class StateFormulaTest {
    private static final Logger log = Logger.getLogger(StateFormulaTest.class.getName());

    void assertSuccessful(Optional<String> result, String state) {
        if (result.isPresent()) {
            assertEquals(state, result.get());
            log.info("Final state: " + result.get());
        } else {
            fail("Final state not found");
        }

    }

    void assertEmpty(Optional<String> result) {
        if (!result.isEmpty()) {
            fail("Final state not found");
        }
    }
    @Test
    void testFormula1() {
        var str = "s1 | s2 & s3 => s4";
        var collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true), Map.entry("s3", true));
        var finalState = StateFormula.parse(str, collectedStates);

        assertSuccessful(finalState, "s4");
        collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s2", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = new HashMap<>();
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s1", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);
    }


    @Test
    void testFormula2() {
        var str = "s1 | (s2 & s3) => s4";
        var collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true), Map.entry("s3", true));
        var finalState = StateFormula.parse(str, collectedStates);

        assertSuccessful(finalState, "s4");
        collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);

        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);

        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);

        assertEmpty(finalState);

        collectedStates = new HashMap<>();
        finalState = StateFormula.parse(str, collectedStates);

        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s1", true));
        finalState = StateFormula.parse(str, collectedStates);

        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);

        assertEmpty(finalState);
    }

    @Test
    void testFormula3() {
        var str = "(s1 & s2) | (s3 & s4) => s5";
        var collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true), Map.entry("s3", true), Map.entry("s4", true));
        var finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s5");

        collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s5");

        collectedStates = Map.ofEntries(Map.entry("s3", true),Map.entry("s4", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s5");

        collectedStates = new HashMap<>();
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s1", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);
    }

    @Test
    void testFormula4() {
        var str = "s1 & s2 & s3 => s4";
        var collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true), Map.entry("s3", true));
        var finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s2", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s2", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = new HashMap<>();
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s1", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);
    }


    @Test
    void testFormula5() {
        var str = "s1 | s2 | s3 => s4";
        var collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true), Map.entry("s3", true));
        var finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s2", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s2", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = new HashMap<>();
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s1", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");
    }


    @Test
    void testFormula6() {
        var str = "(s1 | s2) & s3 => s4";
        var collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true), Map.entry("s3", true));
        var finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s2", true), Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertSuccessful(finalState, "s4");

        collectedStates = Map.ofEntries(Map.entry("s1", true), Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s3", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = new HashMap<>();
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s1", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);

        collectedStates = Map.ofEntries(Map.entry("s2", true));
        finalState = StateFormula.parse(str, collectedStates);
        assertEmpty(finalState);
    }
}