grammar repl;

expression
   : multExpr ((PLUS | MINUS) multExpr)*
   ;

multExpr
   : powExpr ((TIMES | DIV | MOD) powExpr)*
   ;

powExpr
   : atom (POW atom)*
   ;

atom
   : scientific
   | LPAREN expression RPAREN
   | func
   ;

scientific
   : number (E number)?
   ;

number
   : MINUS? DIGIT+ (POINT DIGIT+)?
;

func
   : funcname LPAREN expression RPAREN
   ;

funcname
   : SIN
   | ASIN
   | SQRT
   | ABS
   ;

SIN
   : 'sin'
   ;

ASIN
   : 'asin'
   ;

SQRT
   : 'sqrt'
   ;

ABS
   : 'abs'
   ;

LPAREN
   : '('
   ;


RPAREN
   : ')'
   ;


PLUS
   : '+'
   ;


MINUS
   : '-'
   ;

TIMES
   : '*'
   ;

DIV
   : '/'
   ;

MOD
   : '%'
   ;


POINT
   : '.'
   ;


E
   : 'e' | 'E'
   ;


POW
   : '^'
   ;

DIGIT
   : ('0' .. '9')
   ;


WS
   : [ \r\n\t] + -> channel (HIDDEN)
   ;