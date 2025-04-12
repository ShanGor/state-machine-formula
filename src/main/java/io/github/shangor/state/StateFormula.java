package io.github.shangor.state;

import io.github.shangor.state.generated.StateExprBaseListener;
import io.github.shangor.state.generated.StateExprLexer;
import io.github.shangor.state.generated.StateExprParser;
import lombok.Getter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Map;
import java.util.Optional;

/**
 * Formula parser, to derive final state.
 * Created by shangor on 2017/6/28.
 * @author shangor
 */
public class StateFormula extends StateExprBaseListener {
	private final ParseTreeProperty<Boolean> values = new ParseTreeProperty<>();

    private final Map<String, Boolean> collectedStates;

	@Getter
	private volatile Optional<String> finalState = Optional.empty();

    public StateFormula(Map<String, Boolean> collectedStates) {
        this.collectedStates = collectedStates;
    }

	@Override public void exitExprDerive(StateExprParser.ExprDeriveContext ctx) {
		var booleanResult = values.get(ctx.expr());
		var rightState = ctx.STATE();
		if (booleanResult) {
			finalState = Optional.of(rightState.getText());
		}
	}

	@Override public void exitAndOr(StateExprParser.AndOrContext ctx) {
        if (ctx.STATE().size() != 2) {
            throw new IllegalArgumentException("state size must be 2");
        }
        var leftState = values.get(ctx.STATE(0));
        var rightState = values.get(ctx.STATE(1));
        if (ctx.AND() != null) {
			values.put(ctx, leftState && rightState);
        } else {
			values.put(ctx, leftState || rightState);
        }

		super.exitAndOr(ctx);
    }

	@Override public void exitExprAndOr(StateExprParser.ExprAndOrContext ctx) {
		if (ctx.expr().size() != 2) {
			throw new IllegalArgumentException("state size must be 2");
		}
		var leftState = values.get(ctx.expr(0));
		var rightState = values.get(ctx.expr(1));
		if (ctx.AND() != null) {
			values.put(ctx, leftState && rightState);
		} else {
			values.put(ctx, leftState || rightState);
		}
	}

	@Override
	public void exitParenthesis(StateExprParser.ParenthesisContext ctx) {
		values.put(ctx, values.get(ctx.expr()));
	}

	@Override public void exitState(StateExprParser.StateContext ctx) {
		values.put(ctx, collectedStates.getOrDefault(ctx.getText(), false));
		super.exitState(ctx);
	}

	public static final Optional<String> parse(final String formula, final Map<String, Boolean> collectedStates) {
		return parse(formula, new StateFormula(collectedStates));
	}

	/**
	 * When you are using in a single thread context, you can reuse the stateFormula object.
	 */
	public static final Optional<String> parse(final String formula, final StateFormula stateFormula) {
		var input = CharStreams.fromString(formula);
        var lexer = new StateExprLexer(input);
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new StateExprParser(tokenStream);
        var parseTree = parser.expr();
        var walker = new ParseTreeWalker();
        walker.walk(stateFormula, parseTree);
		return stateFormula.getFinalState();
	}
}
