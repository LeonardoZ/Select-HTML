package com.leonardoz.select_html.model.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class QueryFilters {

    private static final Logger logger = LoggerFactory.getLogger(QueryFilters.class);

    // single filters
    private Optional<BaseFilter> filterExpression;

    public QueryFilters() {
        filterExpression = Optional.empty();
    }

    public QueryFilters(Optional<BaseFilter> filter) {
        this.filterExpression = filter;
    }

    public QueryFilters(BaseFilter filter) {
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
