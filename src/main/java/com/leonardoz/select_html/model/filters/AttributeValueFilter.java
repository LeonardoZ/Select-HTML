package com.leonardoz.select_html.model.filters;

import com.leonardoz.select_html.engine.Concat;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AttributeValueFilter extends BaseFilter<AttributeValueFilter> {

    private final String attribute;
    private final AttributeOperatorType attributeOperatorType;
    private final String value;

    public AttributeValueFilter(String attribute,
                                AttributeOperatorType attributeOperatorType,
                                String value) {
        super(FilterType.ATTRIBUTE_VALUE);
        this.attribute = attribute;
        this.attributeOperatorType = attributeOperatorType;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }

    public AttributeOperatorType getAttributeOperatorType() {
        return attributeOperatorType;
    }

    @Override
    public String asOriginalFilter() {
        return Concat.of(
                "attribute=",
                attribute,
                " ",
                attributeOperatorType.getOriginal(),
                " ",
                value);
    }

    @Override
    public String toQuery() {
        return getFilterType()
                .format(attribute, attributeOperatorType.getOperator(), value);
    }

    @Override
    public int compareTo(AttributeValueFilter other) {
        return this.asOriginalFilter().compareTo(other.asOriginalFilter());
    }

    @Override
    public String toString() {
        return Concat.spaced("attribute", attribute.toString(),
                "attributeOperatorType", attributeOperatorType.toString(),
                "value", value.toString());
    }
}
