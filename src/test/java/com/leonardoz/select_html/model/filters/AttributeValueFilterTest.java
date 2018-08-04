package com.leonardoz.select_html.model.filters;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeValueFilterTest {

    @Test
    public void shouldProduce_AttributeValueFilter_StartWith() {
        AttributeValueFilter filter =
                new AttributeValueFilter("alt",
                        AttributeOperatorType.START_WITH,
                        "img_");
        assertEquals("attribute=alt start with img_", filter.asOriginalFilter());
        assertEquals("[alt^=img_]", filter.toQuery());
    }

    @Test
    public void shouldProduce_AttributeValueFilter_EndWith() {
        AttributeValueFilter filter =
                new AttributeValueFilter("alt",
                        AttributeOperatorType.END_WITH,
                        "_morning");
        assertEquals("attribute=alt end with _morning", filter.asOriginalFilter());
        assertEquals("[alt$=_morning]", filter.toQuery());
    }

    @Test
    public void shouldProduce_AttributeValueFilter_Contains() {
        AttributeValueFilter filter =
                new AttributeValueFilter("alt",
                        AttributeOperatorType.CONTAINS,
                        "orn");
        assertEquals("attribute=alt contains orn", filter.asOriginalFilter());
        assertEquals("[alt*=orn]", filter.toQuery());
    }

    @Test
    public void shouldProduce_AttributeValueFilter_Regex() {
        AttributeValueFilter filter =
                new AttributeValueFilter("alt",
                        AttributeOperatorType.REGEX,
                        "/d{3}+/g");
        assertEquals("attribute=alt regex /d{3}+/g", filter.asOriginalFilter());
        assertEquals("[alt~=/d{3}+/g]", filter.toQuery());
    }

}