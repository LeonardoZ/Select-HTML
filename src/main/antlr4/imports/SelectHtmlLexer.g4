lexer grammar SelectHtmlLexer;


K_SELECT    :   S E L E C T ;
K_FROM      :   F R O M ;
K_WHERE     :   W H E R E ;

K_ATTR      :   (A T T R | A T T R I B U T E) ;
K_ATTRS     :   (A T T R S | A T T R I B U T E S) ;
K_TEXT      :   T E X T ;
K_HTML      :   H T M L ;
K_TAG       :   T A G ;
K_ID        :   I D ;
K_CLASS     :   C L A S S ;
K_CLASSES   :   C L A S S E S;

K_OR        :   O R ;
K_AND       :   A N D ;
K_START     :   S T A R T ;
K_END       :   E N D ;
K_CONTAINS  :   C O N T A I N S ;
K_REGEX     :   R E G E X ;

K_NEXT      :   N E X T ;
K_SIBLING   :   S I B L I N G ;
K_SIBLINGS  :   S I B L I N G S ;
K_DESCENDANT:   D E S C E N D A N T  ;
K_PARENT    :   P A R E N T ;
K_CHILD     :   C H I L D ;

K_OF        :   O F ;
K_WITH      :   W I T H ;

EQUAL       :   '=' ;
ALL         :   '*' ;
OPEN_P      :   '(' ;
CLOSE_P     :   ')' ;

STRING      :    '\'' ('\\'. | '\'\'' | ~('\'' | '\\'))* '\'' ;

LINE_COMMENT    : '//' .*? '\r'? '\n'  -> skip ;
WS              : [ \t\r\n]+ -> skip ;

fragment DIGIT  : [0-9];
fragment LETTER : ('a'..'z'|'A'..'Z');
fragment A      : [aA];
fragment B      : [bB];
fragment C      : [cC];
fragment D      : [dD];
fragment E      : [eE];
fragment F      : [fF];
fragment G      : [gG];
fragment H      : [hH];
fragment I      : [iI];
fragment J      : [jJ];
fragment K      : [kK];
fragment L      : [lL];
fragment M      : [mM];
fragment N      : [nN];
fragment O      : [oO];
fragment P      : [pP];
fragment Q      : [qQ];
fragment R      : [rR];
fragment S      : [sS];
fragment T      : [tT];
fragment U      : [uU];
fragment V      : [vV];
fragment W      : [wW];
fragment X      : [xX];
fragment Y      : [yY];
fragment Z      : [zZ];
fragment SPACE  : ' ';