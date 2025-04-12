//gsCompleted & grCompleted & gdMapped => completed;
//gsCompleted & grCompleted => gsgrCompleted;
//(s1 & s2) | s3 => s4;
//(s1 & s2) | (s3 & s4) => s5;
//(s1 & s2) | (s3 | s4) => s6;

lexer grammar StateBasicTypes;

fragment LETTER: [a-zA-Z_];
fragment DIGIT: [0-9];
STATE: LETTER (LETTER | DIGIT)*;

WS: [ \t\r\n]+ -> skip;
