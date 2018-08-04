package com.leonardoz.select_html.model.projection;

import com.leonardoz.select_html.engine.Concat;

public class TagAttribute extends TagResult {

    private String name;
    private String value;

    public TagAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Concat.of(name, value.isEmpty() ? "" : "=", value);
    }

}
