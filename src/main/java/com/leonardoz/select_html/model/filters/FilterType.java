package com.leonardoz.select_html.model.filters;

import com.leonardoz.select_html.engine.Replace;

public enum FilterType {
    ADJACENT_SIBLING("${1}+${2}", "next sibling"),
    AND("", "and"),
    ATTRIBUTE("[${1}]", "attribute"),
    ATTRIBUTE_VALUE("[${1}${2}${3}]", "attribute"),
    CHILD_OF("${2}>${1}", "child of"),
    CLASS(".${1}", "class"),
    DESCENDANT("${2} ${1}", "descendant of"),
    GENERAL_SIBLINGS("${1}~${2}", "siblings of"),
    ID("#${1}", "id"),
    OR(",", "or"),
    PARENT_OF("${1}>${2}", "parent of"),
    TAG("${1}", "tag");

    private String format;
    private String original;

    FilterType(String format, String original) {
        this.format = format;
        this.original = original;
    }

    public static FilterType of(String key) {
        for (FilterType value : values()) {
            String spaceless = value.original.replace(" ", "");
            if (key.equalsIgnoreCase(spaceless)) {
                return value;
            }
        }
        return null;
    }

    public String getFormat() {
        return format;
    }

    public String format(String... values) {
        return Replace.in(format, values);
    }

    public String getOriginal() {
        return original;
    }

    @Override
    public String toString() {
        return original;
    }
}
