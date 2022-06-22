grammar Expr;

prog: stat+ ;

stat: expr NEWLINE
    | ID '=' expr NEWLINE
    | NEWLINE
    ;

expr: expr ADD term
    | expr SUB term
    | term
    ;

term: term MUL factor
    | term DIV factor
    | factor
    ;

factor: '(' expr ')'
    | INT
    | ID
    ;

MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;

ID : [a-zA-Z]+ ;

INT : [0-9]+ ;

NEWLINE : '\r'? '\n' ;

WS : [ \t]+ -> skip;
