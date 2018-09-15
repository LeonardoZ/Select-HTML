package com.leonardoz.select_html.parser.ast.expressions;

import com.leonardoz.select_html.engine.Concat;

public class ComposedFilter extends BaseFilter {

    private BaseFilter leftFilter;
    private BaseFilter rightFilter;

    public ComposedFilter(FilterType filterType,
                          BaseFilter leftFilter,
                          BaseFilter rightFilter) {
        super(filterType);
        this.leftFilter = leftFilter;
        this.rightFilter = rightFilter;
    }

    public BaseFilter getLeftFilter() {
        return leftFilter;
    }

    public BaseFilter getRightFilter() {
        return rightFilter;
    }


    @Override
    public String asOriginalFilter() {
        return Concat.of(
                leftFilter.asOriginalFilter(),
                " ",
                getFilterType().getOriginal(),
                " ",
                rightFilter.asOriginalFilter());
    }

    @Override
    public String toQuery() {
        return getFilterType()
                .format(leftFilter.toQuery(), rightFilter.toQuery());
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String toString() {
        return Concat.spaced("left", leftFilter.toString(),
                getFilterType().toString(),
                "right", rightFilter.toString());
    }
}
