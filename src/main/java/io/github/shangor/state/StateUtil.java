package io.github.shangor.state;

import io.github.shangor.state.generated.StateExprLexer;
import io.github.shangor.state.generated.StateExprParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.*;

/**
 * State Formula Utility to parse state formulas and get some useful results.
 * @author shangor
 */
public class StateUtil {
    /**
     * With given formulas, parse them and derive the output.
     */
    public static Optional<String> determineState(String comingState, String stateFormulas, Map<String, Boolean> collectedStates) {
        if (StateUtil.isBlank(stateFormulas) || collectedStates == null || collectedStates.isEmpty()) {
            return Optional.empty();
        }

        var collected = new HashMap<String, Boolean>();
        collected.putAll(collectedStates);
        collected.put(comingState, true);
        var stateFormula = new StateFormula(collected);

        var formulas = stateFormulas.split(";");
        for (var f : formulas) {
            if (StateUtil.isNotBlank(f)) {
                var stateOpt = StateFormula.parse(f.trim(), stateFormula);
                if (stateOpt.isPresent()) {
                    return stateOpt;
                }
            }
        }
        return Optional.empty();
    }

    public static Pair<Set<String>, Set<String>> parseFormulaForInput(String formula) {
        var input = CharStreams.fromString(formula);
        var lexer = new StateExprLexer(input);
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new StateExprParser(tokenStream);
        FormularIdCollector collector = new FormularIdCollector();
        ParseTreeWalker.DEFAULT.walk(collector, parser.expr());
        return new Pair<>(collector.getVariables(), collector.getPossibleResults());
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
