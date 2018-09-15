package com.leonardoz.select_html.parser.ast;

import com.leonardoz.select_html.parser.ast.expressions.BaseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class WhereClause {

    private static final Logger logger = LoggerFactory.getLogger(WhereClause.class);

    // single expressions
    private Optional<BaseFilter> filterExpression;

    public WhereClause() {
        filterExpression = Optional.empty();
    }

    public WhereClause(Optional<BaseFilter> filter) {
        this.filterExpression = filter;
    }

    public WhereClause(BaseFilter filter) {
        filterExpression = Optional.empty();
        this.filterExpression = Optional.of(filter);
    }

    public String generate() {
        String query = filterExpression.isPresent() ?
                filterExpression.get().toQuery() :
                "*";
        logger.debug("Generated query: " + query);
        return query;
    }

}
