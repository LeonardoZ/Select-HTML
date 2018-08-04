package com.leonardoz.select_html.engine;

import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;

public class Replace {

    public static String in(String format, String... values) {
        Map<String, String> params = new HashMap<>();
        for (int i = 1; i < values.length + 1; i++) {
            params.put(Integer.toString(i), values[i - 1]);
        }
        StrSubstitutor sub = new StrSubstitutor(params);
        return sub.replace(format);
    }

    public static String quotes(String format) {
        return format.replace("'", "");
    }

}
