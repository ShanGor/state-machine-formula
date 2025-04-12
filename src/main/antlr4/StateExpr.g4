grammar StateExpr;
@header {
    package io.github.shangor.state.generated;
}

import StateOperators, StateBasicTypes;

expr: STATE                                  # State
    | STATE (AND|OR) STATE                   # AndOr
    | '(' expr ')'                           # Parenthesis
    | expr (AND|OR) expr                     # ExprAndOr
    | expr DERIVE STATE                      # ExprDerive
    ;