grammar LibExpr ;
import CommonLexerRules ;

prog : stat+ ;

stat : expr NEWLINE
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
