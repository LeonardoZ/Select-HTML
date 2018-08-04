package com.leonardoz.select_html.model.projection;

public class TagClass extends TagResult {

    private String value;

    public TagClass(String value) {
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
