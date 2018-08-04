package com.leonardoz.select_html.model.filters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComposedFilterTest.class, OrOperatorFilterTest.class,
        AndOperatorFilterTest.class, KeyValueFilterTest.class,
        AttributeValueFilterTest.class, QueryFiltersTest.class})
public class AllUnitTests {
}
