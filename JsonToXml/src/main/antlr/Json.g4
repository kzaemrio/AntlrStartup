
/** Taken from "The Definitive ANTLR 4 Reference" by Terence Parr */

// Derived from http://json.org
grammar Json;

json
   : value
   ;

value
   : STRING # String
   | NUMBER # Atom
   | obj #ObjectValue
   | array #ArrayValue
   | 'true' #Atom
   | 'false' #Atom
   | 'null' #Atom
   ;

obj
   : '{' pair (',' pair)* '}' #AnObject
   | '{' '}' #EmptyObject
   ;

pair
   : STRING ':' value
   ;

array
   : '[' value (',' value)* ']' #ArrayOfValues
   | '[' ']' #EmptyArray
   ;


STRING
   : '"' (ESC | SAFECODEPOINT)* '"'
   ;

NUMBER
   : '-'? INT ('.' [0-9] +)? EXP?
   ;

// \- since - means "range" inside [...]

WS
   : [ \t\r\n] + -> skip
   ;

fragment ESC
   : '\\' (["\\/bfnrt] | UNICODE)
   ;

fragment UNICODE
   : 'u' HEX HEX HEX HEX
   ;

fragment HEX
   : [0-9a-fA-F]
   ;

fragment SAFECODEPOINT
   : ~ ["\\\u0000-\u001F]
   ;

fragment INT
   : '0' | [1-9] [0-9]*
   ;

// no leading zeros

fragment EXP
   : [Ee] [+\-]? INT
   ;
