package com.leonardoz.select_html.parser.ast.expressions;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrOperatorFilterTest {

    @Test
    public void shouldProduce_TagOrId() throws Exception {
        KeyValueFilter div = new KeyValueFilter(FilterType.TAG, "tag", "div");
        KeyValueFilter p = new KeyValueFilter(FilterType.TAG, "tag", "p");
        OrOperatorFilter filter = new OrOperatorFilter(div, p);
        assertEquals("tag=div or tag=p", filter.asOriginalFilter());
        assertEquals("div,p", filter.toQuery());
    }

    @Test
    public void shouldProduce_MultipleElements() throws Exception {
        KeyValueFilter div = new KeyValueFilter(FilterType.TAG, "tag", "div");
        KeyValueFilter p = new KeyValueFilter(FilterType.TAG, "tag", "p");
        ComposedFilter cf = new ComposedFilter(FilterType.ADJACENT_SIBLING, div, p);

        KeyValueFilter prods = new KeyValueFilter(FilterType.CLASS, "class", "prods");
        OrOperatorFilter filter = new OrOperatorFilter(cf, prods);
        assertEquals("tag=div next sibling tag=p or class=prods", filter.asOriginalFilter());
        assertEquals("div+p,.prods", filter.toQuery());
    }

}