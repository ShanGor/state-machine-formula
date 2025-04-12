//gsCompleted & grCompleted & gdMapped => completed;
//gsCompleted & grCompleted => gsgrCompleted;
//(s1 & s2) | s3 => s4;
//(s1 & s2) | (s3 & s4) => s5;
//(s1 & s2) | (s3 | s4) => s6;

lexer grammar StateOperators;

AND: '&';
OR: '|';
DERIVE: '=>';