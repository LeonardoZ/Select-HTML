package com.leonardoz.select_html.parser.ast.filters;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeyValueFilterTest {

    @Test
    public void shouldProduce_AttributeQuery() throws Exception {
        KeyValueFilter attrFilter = new KeyValueFilter(
                FilterType.ATTRIBUTE,
                "attribute",
                "text");
        assertEquals("attribute", attrFilter.getKey());
        assertEquals("text", attrFilter.getValue());
        assertEquals("attribute=text", attrFilter.asOriginalFilter());
        assertEquals("[text]", attrFilter.toQuery());
    }

    @Test
    public void shouldProduce_AttributeValueQuery() throws Exception {
        KeyValueFilter attrFilter = new KeyValueFilter(
                FilterType.ATTRIBUTE,
                "attribute",
                "text");
        assertEquals("attribute", attrFilter.getKey());
        assertEquals("text", attrFilter.getValue());
        assertEquals("attribute=text", attrFilter.asOriginalFilter());
        assertEquals("[text]", attrFilter.toQuery());
    }

    @Test
    public void shouldProduce_IdQuery() throws Exception {
        KeyValueFilter attrFilter = new KeyValueFilter(
                FilterType.ID,
                "id",
                "prod_123");
        assertEquals("id", attrFilter.getKey());
        assertEquals("prod_123", attrFilter.getValue());
        assertEquals("id=prod_123", attrFilter.asOriginalFilter());
        assertEquals("#prod_123", attrFilter.toQuery());
    }

    @Test
    public void shouldProduce_ClassQuery() throws Exception {
        KeyValueFilter attrFilter = new KeyValueFilter(
                FilterType.CLASS,
                "class",
                "products");
        assertEquals("class", attrFilter.getKey());
        assertEquals("products", attrFilter.getValue());
        assertEquals("class=products", attrFilter.asOriginalFilter());
        assertEquals(".products", attrFilter.toQuery());
    }

    @Test
    public void shouldProduce_TagQuery() throws Exception {
        KeyValueFilter attrFilter = new KeyValueFilter(
                FilterType.TAG,
                "tag",
                "div");
        assertEquals("tag", attrFilter.getKey());
        assertEquals("div", attrFilter.getValue());
        assertEquals("tag=div", attrFilter.asOriginalFilter());
        assertEquals("div", attrFilter.toQuery());
    }


}