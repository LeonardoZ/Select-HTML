package com.leonardoz.select_html.parser.ast;

import com.leonardoz.select_html.parser.ast.expressions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class WhereClauseTest {

    @Test
    public void shouldGenerate_WithFilters() throws Exception {
        // div#prod > p.price - and child of and
        KeyValueFilter div = new KeyValueFilter(FilterType.TAG, "tag", "div");
        KeyValueFilter prod = new KeyValueFilter(FilterType.ID, "id", "prod");
        AndOperatorFilter divAndProd = new AndOperatorFilter(div, prod);

        KeyValueFilter p = new KeyValueFilter(FilterType.TAG, "tag", "p");
        KeyValueFilter price = new KeyValueFilter(FilterType.CLASS, "class", "price");
        AndOperatorFilter pAndPrice = new AndOperatorFilter(p, price);

        ComposedFilter childOf = new ComposedFilter(FilterType.PARENT_OF, divAndProd, pAndPrice);
        WhereClause whereClause = new WhereClause(childOf);
        assertEquals("div#prod>p.price", whereClause.generate());
    }

    @Test
    public void shouldGenerate_WithAndFilters() throws Exception {
        // a[href].highlight
        // tag=a and attribute=href and class = highlight
        KeyValueFilter a = new KeyValueFilter(FilterType.TAG, "tag", "a");
        KeyValueFilter href= new KeyValueFilter(FilterType.ATTRIBUTE, "attribute", "href");
        AndOperatorFilter aAndHref = new AndOperatorFilter(a, href);

        KeyValueFilter highlight= new KeyValueFilter(FilterType.CLASS, "class", "highlight");
        AndOperatorFilter complete = new AndOperatorFilter(aAndHref, highlight);
        WhereClause whereClause = new WhereClause(complete);
        assertEquals("a[href].highlight", whereClause.generate());
    }

    @Test
    public void shouldGenerate_WithOrFilters() throws Exception {
        // a[href].highlight
        // tag=a and attribute=href or class = highlight
        KeyValueFilter a = new KeyValueFilter(FilterType.TAG, "tag", "a");
        KeyValueFilter href= new KeyValueFilter(FilterType.ATTRIBUTE, "attribute", "href");
        AndOperatorFilter aAndHref = new AndOperatorFilter(a, href);

        KeyValueFilter highlight= new KeyValueFilter(FilterType.CLASS, "class", "highlight");
        OrOperatorFilter complete = new OrOperatorFilter(aAndHref, highlight);
        WhereClause whereClause = new WhereClause(complete);
        assertEquals("a[href],.highlight", whereClause.generate());
    }

    @Test
    public void shouldGenerate_WithoutFilters() throws Exception {
        WhereClause whereClause = new WhereClause();
        assertEquals("*", whereClause.generate());
    }

}