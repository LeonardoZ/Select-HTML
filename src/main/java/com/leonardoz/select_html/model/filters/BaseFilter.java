package com.leonardoz.select_html.model.filters;

public abstract class BaseFilter<T extends BaseFilter<T>> implements QueryString, Comparable<T> {

    private FilterType filterType;

    public BaseFilter(FilterType filterType) {
        this.filterType = filterType;
    }

    public FilterType getFilterType() {
        return filterType;
    }

}
