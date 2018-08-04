package com.leonardoz.select_html.model.filters;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComposedFilterTest {

    @Test
    public void shouldProduce_ChildOfQuery() throws Exception {
        FilterType child = FilterType.CHILD_OF;
        KeyValueFilter leftFilter = new KeyValueFilter(FilterType.ID, "id", "prod123");
        KeyValueFilter rightFilter = new KeyValueFilter(FilterType.TAG, "tag", "div");
        ComposedFilter composedFilter = new ComposedFilter(child, leftFilter, rightFilter);
        assertEquals(leftFilter, composedFilter.getLeftFilter());
        assertEquals(rightFilter, composedFilter.getRightFilter());
        assertEquals("id=prod123 child of tag=div", composedFilter.asOriginalFilter());
        assertEquals("div>#prod123", composedFilter.toQuery());
    }

    @Test
    public void shouldProduce_ParentOfQuery() throws Exception {
        FilterType child = FilterType.PARENT_OF;
        KeyValueFilter leftFilter = new KeyValueFilter(FilterType.TAG, "tag", "div");
        KeyValueFilter rightFilter = new KeyValueFilter(FilterType.ID, "id", "prod123");
        ComposedFilter composedFilter = new ComposedFilter(child, leftFilter, rightFilter);
        assertEquals(leftFilter, composedFilter.getLeftFilter());
        assertEquals(rightFilter, composedFilter.getRightFilter());
        assertEquals("tag=div parent of id=prod123", composedFilter.asOriginalFilter());
        assertEquals("div>#prod123", composedFilter.toQuery());
    }

    @Test
    public void shouldProduce_AdjacentSiblingOfQuery() throws Exception {
        FilterType child = FilterType.ADJACENT_SIBLING;
        KeyValueFilter leftFilter = new KeyValueFilter(FilterType.TAG, "tag", "div");
        KeyValueFilter rightFilter = new KeyValueFilter(FilterType.TAG, "tag", "p");
        ComposedFilter composedFilter = new ComposedFilter(child, leftFilter, rightFilter);
        assertEquals(leftFilter, composedFilter.getLeftFilter());
        assertEquals(rightFilter, composedFilter.getRightFilter());
        assertEquals("tag=div next sibling tag=p", composedFilter.asOriginalFilter());
        assertEquals("div+p", composedFilter.toQuery());
    }


    @Test
    public void shouldProduce_GeneralSiblingsOfQuery() throws Exception {
        FilterType child = FilterType.GENERAL_SIBLINGS;
        KeyValueFilter leftFilter = new KeyValueFilter(FilterType.TAG, "tag", "div");
        KeyValueFilter rightFilter = new KeyValueFilter(FilterType.TAG, "tag", "p");
        ComposedFilter composedFilter = new ComposedFilter(child, leftFilter, rightFilter);
        assertEquals(leftFilter, composedFilter.getLeftFilter());
        assertEquals(rightFilter, composedFilter.getRightFilter());
        assertEquals("tag=div siblings of tag=p", composedFilter.asOriginalFilter());
        assertEquals("div~p", composedFilter.toQuery());
    }

    @Test
    public void shouldProduce_DescendantOfQuery() throws Exception {
        FilterType descendant = FilterType.DESCENDANT;
        KeyValueFilter leftFilter = new KeyValueFilter(FilterType.CLASS, "class", "products");
        KeyValueFilter rightFilter  = new KeyValueFilter(FilterType.TAG, "tag", "div");
        ComposedFilter composedFilter = new ComposedFilter(descendant, leftFilter, rightFilter);
        assertEquals(leftFilter, composedFilter.getLeftFilter());
        assertEquals(rightFilter, composedFilter.getRightFilter());
        assertEquals("class=products descendant of tag=div", composedFilter.asOriginalFilter());
        assertEquals("div .products", composedFilter.toQuery());
    }

}