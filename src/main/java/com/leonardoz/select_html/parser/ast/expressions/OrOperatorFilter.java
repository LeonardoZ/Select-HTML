package com.leonardoz.select_html.parser.ast.filters;

import com.leonardoz.select_html.engine.Concat;

public class OrOperatorFilter extends BaseFilter<OrOperatorFilter> {

    private QueryString left;
    private QueryString right;

    public OrOperatorFilter(QueryString left, QueryString right) {
        super(FilterType.OR);
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
        return Concat.of(left.asOriginalFilter()," or ", right.asOriginalFilter());
    }

    @Override
    public String toQuery() {
        return Concat.of(left.toQuery(),",", right.toQuery());
    }

    @Override
    public int compareTo(OrOperatorFilter other) {
        return this.asOriginalFilter().compareTo(other.asOriginalFilter());
    }

    @Override
    public String toString() {
        return Concat.spaced("leftFilter", left.toString(),
                getFilterType().toString(),
                "rightFilter", right.toString());
    }

}
