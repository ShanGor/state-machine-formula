# State Machine Formula
A DSL to derive state flow from a set of state combinations.
  > With Antlr4 defined DSL, we are able to derive a final state by different state combinations.
  >  ```
  >  s1 & s2 & s3 => s4;
  >  s1 | s2 & s3 => s4;
  >  (s1 & s2) | (s3 & s4) => s5;
  >  ```


## Contributors
- Emerson Z CHEN