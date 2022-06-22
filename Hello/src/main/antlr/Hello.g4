
grammar Hello;

root : 'hello' ID;

ID : [a-z]+;
WS : [ \t\r\n]+ -> skip;
