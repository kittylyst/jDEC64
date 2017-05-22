grammar DEC64Repl;

@header {
   package dec64.repl;
}

expression
   : (PLUS | MINUS) atom
   | left=expression (PLUS | MINUS) right=expression
   | left=expression (TIMES | DIV | MOD) right=expression
   | atom
   ;

atom
   : number
//   : scientific
   | base=number POW power=number
   | LPAREN expression RPAREN
   | func
   ;

scientific
   : number (E number)?
   ;

number
   : MINUS? wholepart=DIGIT+ (POINT fracpart=DIGIT+)?
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
