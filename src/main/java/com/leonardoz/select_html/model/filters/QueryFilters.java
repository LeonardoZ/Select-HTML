package com.leonardoz.select_html.model.filters;

import java.util.Optional;

public class QueryFilters {

    /**
     * #### Key value
     * ID
     * #va
     * id = "va"
     * <p>
     * Class
     * .container
     * class = "container"
     * <p>
     * Tag
     * div
     * tag = "div"
     * <p>
     * Attribute
     * [text]
     * attribute = "text"
     * <p>
     * <p>
     * #### Composed
     * <p>
     * Attribute/value
     * [text="nome"]
     * attribute = "text" [contains/starts with/ends with/regex] "nome"
     * <p>
     * Sibling
     * [id|class|tag|attribute|attribute/value] = ""
     * sibling of
     * [id|class|tag|attribute|attribute/value] =
     * <p>
     * Child
     * [id|class|tag|attribute|attribute/value] = ""
     * child of
     * [id|class|tag|attribute|attribute/value] =
     * <p>
     * Descendant
     * [id|class|tag|attribute|attribute/value] = ""
     * descendant of
     * [id|class|tag|attribute|attribute/value] =
     * <p>
     * <p>
     * where
     * ((tag = a) and ((attribute = (href = text))))
     * tag
     */

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
        return filterExpression.isPresent() ?
                filterExpression.get().toQuery() :
                "*";
    }

}
