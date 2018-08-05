package com.leonardoz.select_html.model.filters;

public enum AttributeOperatorType {
    CONTAINS("*=", "contains"),
    END_WITH("$=", "end with"),
    EQUALITY("=", "="),
    REGEX("~=", "regex"),
    START_WITH("^=", "start with");

    private final String operator;
    private String original;

    AttributeOperatorType(String operator, String original) {
        this.operator = operator;
        this.original = original;
    }

    public static AttributeOperatorType of(String operator) {
        for (AttributeOperatorType type : values()) {
            String spaceless = type.original.replaceAll(" ", "");
            if (operator.equalsIgnoreCase(spaceless)) {
                return type;
            }
        }
        return null;
    }

    public String getOperator() {
        return operator;
    }

    public String getOriginal() {
        return original;
    }

    @Override
    public String toString() {
        return original;
    }
}
