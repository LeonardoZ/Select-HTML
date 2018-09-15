package com.leonardoz.select_html.parser.ast.expressions;

import com.leonardoz.select_html.parser.ast.WhereClauseTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComposedFilterTest.class, OrOperatorFilterTest.class,
        AndOperatorFilterTest.class, KeyValueFilterTest.class,
        AttributeValueFilterTest.class, WhereClauseTest.class})
public class AllUnitTests {
}
