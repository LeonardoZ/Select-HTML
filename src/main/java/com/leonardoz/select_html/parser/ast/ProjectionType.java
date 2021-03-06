package com.leonardoz.select_html.parser.ast;

public enum ProjectionType {

    TAG, ATTRIBUTES, ATTRIBUTE, CLASSES, CLASS, ID, HTML, TEXT, ALL;

    public static ProjectionType of(String source) {
        for (ProjectionType type : values()) {
            if (source.equalsIgnoreCase(type.name())) {
                return type;
            }
        }
        return null;
    }


}
