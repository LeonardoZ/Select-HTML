grammar SelectHtmlParser ;

import SelectHtmlLexer ;

statement : selectClause fromClause whereClause? EOF;

selectClause : K_SELECT projection ;
projection : projectionTypes (',' projectionTypes)* ;
projectionTypes : ((K_TAG|K_ID|K_CLASS|K_ATTR|K_ATTRS|K_TEXT|K_HTML)| ALL) ;

fromClause : K_FROM STRING ;

whereClause : K_WHERE whereExpression+ ;
whereExpression  : whereExpression composedOperators whereExpression   #composedFilterExpression
                 | whereExpression andOrOperators whereExpression      #andOrFilterExpression
                 | attributeValueFilter                                #attributeValueFilterExpression
                 | keyValueFilter                                      #keyValueFilterExpression
                 | OPEN_P whereExpression CLOSE_P                      #parenthesisExpression

                 ;

attributeValueFilter : K_ATTR EQUAL STRING attributeValueOperators STRING ;
keyValueFilter : filter EQUAL STRING;
filter : (K_TAG|K_ID|K_CLASS|K_ATTR) ;

composedOperators : (K_NEXT K_SIBLING|K_SIBLINGS K_OF|K_DESCENDANT K_OF|K_PARENT K_OF|K_CHILD K_OF) ;
attributeValueOperators : (EQUAL|K_START K_WITH|K_END K_WITH|K_CONTAINS|K_REGEX) ;
andOrOperators : (K_AND|K_OR) ;