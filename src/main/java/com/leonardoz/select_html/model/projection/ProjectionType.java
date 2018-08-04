package com.leonardoz.select_html.model.projection;

public enum ProjectionType {

    TAG, ATTRIBUTES, ATTRIBUTE, CLASSES, CLASS, ID, HTML, TEXT;

    public static ProjectionType of(String source) {
        for (ProjectionType type : values()) {
            if (source.equalsIgnoreCase(type.name())) {
                return type;
            }
        }
        return null;
    }


}
