package io.github.shangor.state;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StateUtilTest {

    @Test
    void determineState() {
        // Test case 1
        var collectedStates = Map.of("gsCompleted", true, "grCompleted", true, "gdMapped", true);
        var comingState = "completed";
        var stateFormulas = "gsCompleted & grCompleted & gdMapped => completed;";
        var result = StateUtil.determineState(comingState, stateFormulas, collectedStates);
        assertTrue(result.isPresent());
        assertEquals("completed", result.get());

        // Test case 2
        stateFormulas = """
                        gsCompleted & grCompleted & gdMapped => completed;
                        gsCompleted & grCompleted => gsgrCompleted
                        """;
        collectedStates = Map.of("gsCompleted", true);
        comingState = "grCompleted";
        result = StateUtil.determineState(comingState, stateFormulas, collectedStates);
        assertTrue(result.isPresent());
        assertEquals("gsgrCompleted", result.get());
        // Test case 2.1
        collectedStates = Map.of("gsCompleted", true, "grCompleted", true);
        comingState = "gdMapped";
        result = StateUtil.determineState(comingState, stateFormulas, collectedStates);
        assertTrue(result.isPresent());
        assertEquals("completed", result.get());
    }

    @Test
    void parseFormulaForInput() {
        var formula = "s1 & s2 & s3 => s4";
        var formulas = StateUtil.parseFormulaForInput(formula);
        var vars = formulas.getLeft();
        var possibleResults = formulas.getRight();
        assertTrue(vars.contains("s1"));
        assertTrue(vars.contains("s2"));
        assertTrue(vars.contains("s3"));

        assertTrue(possibleResults.contains("s4"));
    }
}