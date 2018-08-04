package com.leonardoz.select_html.model.projection;

public class TagId extends TagResult {

    private String value;

    public TagId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
