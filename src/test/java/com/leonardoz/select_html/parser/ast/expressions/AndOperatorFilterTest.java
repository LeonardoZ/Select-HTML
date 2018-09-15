package com.leonardoz.select_html.parser.ast.expressions;

import org.junit.Test;

import static org.junit.Assert.*;

public class AndOperatorFilterTest {

    @Test
    public void shouldAppend_TagAndId() throws Exception {
        KeyValueFilter tag = new KeyValueFilter(FilterType.TAG, "tag", "div");
        KeyValueFilter id = new KeyValueFilter(FilterType.ID, "id", "product_z");
        AndOperatorFilter filter = new AndOperatorFilter(tag, id);
        assertEquals("tag=div and id=product_z", filter.asOriginalFilter());
        assertEquals("div#product_z", filter.toQuery());
    }

    @Test
    public void shouldAppend_TagAndClass() throws Exception {
        KeyValueFilter tag = new KeyValueFilter(FilterType.TAG, "tag", "div");
        KeyValueFilter id = new KeyValueFilter(FilterType.CLASS, "class", "products");
        AndOperatorFilter filter = new AndOperatorFilter(tag, id);
        assertEquals("tag=div and class=products", filter.asOriginalFilter());
        assertEquals("div.products", filter.toQuery());
    }

    @Test
    public void shouldAppend_AndFilterAndComposedFilter () throws Exception {
        KeyValueFilter tag = new KeyValueFilter(FilterType.TAG, "tag", "div");
        KeyValueFilter klass = new KeyValueFilter(FilterType.CLASS, "class", "prices");
        AndOperatorFilter parent = new AndOperatorFilter(tag, klass);

        KeyValueFilter child = new KeyValueFilter(FilterType.TAG, "tag", "p");
        ComposedFilter filter = new ComposedFilter(FilterType.PARENT_OF, parent, child);

        assertEquals("tag=div and class=prices parent of tag=p", filter.asOriginalFilter());
        assertEquals("div.prices>p", filter.toQuery());
    }
}