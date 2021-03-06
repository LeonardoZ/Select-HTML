package com.leonardoz.select_html.engine;

public class Concat {

    public static String of(String...values) {
        StringBuilder builder = new StringBuilder();
        for (String v : values) {
            builder.append(v);
        }
        return builder.toString();
    }

    public static String spaced(String...values) {
        StringBuilder builder = new StringBuilder();
        for (String v : values) {
            builder.append(v);
            builder.append(" ");
        }
        return builder.toString().trim();
    }


}
