package io.github.shangor.state;

import io.github.shangor.state.generated.StateExprBaseListener;
import io.github.shangor.state.generated.StateExprParser;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * To help the parser to collect all the variables and possible results.
 */
@Getter
public class FormularIdCollector extends StateExprBaseListener {
    private final Set<String> variables = new HashSet<>();
    private final Set<String> possibleResults = new HashSet<>();

    @Override
    public void enterState(StateExprParser.StateContext ctx) {
        var expr = ctx.getText();
        variables.add(expr);
    }

    @Override
    public void enterExprDerive(StateExprParser.ExprDeriveContext ctx) {
        possibleResults.add(ctx.STATE().getText());
    }
}
