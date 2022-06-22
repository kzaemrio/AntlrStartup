grammar LabeledExpr ;
import CommonLexerRules ;

prog : stat+ ;

stat : expr NEWLINE #printExpr
    | ID '=' expr NEWLINE #assign
    | NEWLINE #blank
    ;

expr: expr ADD term #add
    | expr SUB term #sub
    | term #termValue
    ;

term: term MUL factor #mul
    | term DIV factor #div
    | factor #factorValue
    ;

factor: '(' expr ')' #par
    | INT #int
    | ID #id
    ;
