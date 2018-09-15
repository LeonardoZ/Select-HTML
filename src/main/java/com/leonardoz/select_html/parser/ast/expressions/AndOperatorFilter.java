package com.leonardoz.select_html.parser.ast.expressions;

import com.leonardoz.select_html.engine.Concat;

public class AndOperatorFilter extends BaseFilter<AndOperatorFilter> {

    private QueryString left;
    private QueryString right;

    public AndOperatorFilter(QueryString left, QueryString right) {
        super(FilterType.AND);
        this.left = left;
        this.right = right;
    }

    public QueryString getLeft() {
        return left;
    }

    public QueryString getRight() {
        return right;
    }

    @Override
    public String asOriginalFilter() {
        return Concat.of(left.asOriginalFilter(), " and ", right.asOriginalFilter());
    }

    @Override
    public String toQuery() {
        return Concat.of(left.toQuery(), right.toQuery());
    }

    @Override
    public int compareTo(AndOperatorFilter other) {
        return this.asOriginalFilter().compareTo(other.asOriginalFilter());
    }

    @Override
    public String toString() {
        return Concat.spaced("leftFilter", left.toString(),
                getFilterType().toString(),
                "rightFilter", right.toString());
    }
}
