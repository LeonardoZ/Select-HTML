package com.leonardoz.select_html.model.filters;

import com.leonardoz.select_html.engine.Concat;

public class KeyValueFilter extends BaseFilter<KeyValueFilter> {

    private final String key;
    private final String value;

    public KeyValueFilter(FilterType filterType, String key, String value) {
        super(filterType);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String asOriginalFilter() {
        return Concat.of(key, "=", value);
    }

    @Override
    public String toQuery() {
        return getFilterType().format(value);
    }

    @Override
    public int compareTo(KeyValueFilter other) {
        return this.asOriginalFilter().compareTo(other.asOriginalFilter());
    }

    @Override
    public String toString() {
        return Concat.spaced(getFilterType().toString(),
                value);
    }
}
